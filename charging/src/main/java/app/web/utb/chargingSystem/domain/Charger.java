package app.web.utb.chargingSystem.domain;

import app.web.utb.alarm.domain.AlarmType;
import app.web.utb.chargingSystem.domain.event.*;
import app.web.utb.chargingSystem.infrastructure.persistence.ChargerTableName;
import app.web.utb.domainDrivenDesign.event.DomainEvent;
import app.web.utb.validator.CommonValidators;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = ChargerTableName.CHARGER_TABLE_NAME)
@NoArgsConstructor
class Charger extends app.web.utb.domainDrivenDesign.superClassEntity.Entity {
    private static final long SERVICE_TAG_ID = 255L;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "assigned_carts", joinColumns = @JoinColumn(name = "charger_id"))
    @Column(name = "cart_id")
    private final Set<Long> assignedCarts = new HashSet<>();
    @Getter(value = AccessLevel.PACKAGE)
    private int position;
    private long currentCart;
    @Embedded
    private ChargerState chargerState;

    Charger(int position) {
        this.position = position;
        this.chargerState = new ChargerState();
        validate();
    }

    private void validate() {
        CommonValidators.validatePositive(this.position, "pozycja prostownika");
    }

    boolean hasPosition(int chargerPosition) {
        return this.position == chargerPosition;
    }

    void assignCart(long cartId) {
        this.assignedCarts.add(cartId);
    }

    boolean detachCart(long cartId) {
        return this.assignedCarts.remove(cartId);
    }

    List<DomainEvent> updateData(ChargerDataFromController dataFromController) {
        ArrayList<DomainEvent> events = new ArrayList<>();
        assignChargingCurrent(dataFromController);
        events.addAll(detectChargingInterruption(dataFromController));
        events.addAll(cartMatchToCharger(dataFromController.getSystemId(), dataFromController.getCartId()));
        events.addAll(chargingDetection(dataFromController.getSystemId(), dataFromController.isCharging(), dataFromController.getCartId()));
        return events;
    }

    private List<DomainEvent> chargingDetection(long systemId, boolean chargingState, long cartId) {
        ArrayList<DomainEvent> events = new ArrayList<>();
        if (chargingStarted(chargingState))
            events.add(new ChargingStarted(systemId, cartId, this.position));
        if (chargingStopped(chargingState))
            events.add(new ChargingStopped(systemId, cartId, position));
        this.chargerState = this.chargerState.assignCharging(chargingState);
        if (chargingWithoutCart(chargingState, cartId)) {
            events.add(new ChargingWithoutCartDetected(systemId, this.position));
            this.chargerState = this.chargerState.assignChargingWithoutCartError();
        }
        return events;
    }

    private boolean chargingWithoutCart(boolean chargingState, long cartId) {
        return chargingStarted(chargingState) && cartId == 0 && !this.chargerState.isChargingWithoutCartError() && !this.chargerState.isChargingInterruptionError();
    }

    private boolean chargingStopped(boolean chargingState) {
        return this.chargerState.chargingStopped(chargingState);
    }

    private boolean chargingStarted(boolean chargingState) {
        return this.chargerState.chargingStarted(chargingState);
    }

    private List<DomainEvent> cartMatchToCharger(long systemId, long cartId) {
        ArrayList<DomainEvent> events = new ArrayList<>();

        if (cartNotMatchToCharger(cartId)) {
            this.chargerState = this.chargerState.assignCartNotMatchError();
            events.add(new CartNotMatchedToCharger(systemId, cartId, this.position));
        }
        this.currentCart = cartId;
        return events;
    }

    private boolean cartNotMatchToCharger(long cartId) {
        boolean cartMatch = this.assignedCarts.stream()
                .anyMatch(assignedCart -> assignedCart == cartId);
        return !cartMatch && cartDetected(cartId) && !this.chargerState.isCartNotMatchError() && cartId != SERVICE_TAG_ID;
    }

    private boolean cartDetected(long cartId) {
        return cartId != 0L;
    }

    private List<DomainEvent> detectChargingInterruption(ChargerDataFromController dataFromController) {
        List<DomainEvent> events = new ArrayList<>();
        if (chargingInterrupted(dataFromController.isChargingInterrupted())) {
            events.add(new ChargingInterrupted(dataFromController.getSystemId(), this.position, dataFromController.getCartId()));
            this.chargerState = this.chargerState.assignInterruptionError();
        }
        return events;
    }

    private boolean chargingInterrupted(boolean chargingInterrupted) {
        return !this.chargerState.isChargingInterruptionError() && chargingInterrupted;
    }

    private void assignChargingCurrent(ChargerDataFromController dataFromController) {
        this.chargerState = chargerState.assignCurrent(dataFromController.getCurrent());
    }

    boolean hasCart(long cartId) {
        return this.assignedCarts.stream()
                .anyMatch(assignedCartId -> assignedCartId == cartId);
    }

    void confirmError(AlarmType alarmType) {
        switch (alarmType) {
            case CHARGING_INTERRUPTED -> this.chargerState = this.chargerState.confirmChargingInterruptionError();
            case CART_NOT_MATCH -> this.chargerState = this.chargerState.confirmCartNotMatchError();
            case CHARGING_WITHOUT_CART -> this.chargerState = this.chargerState.confirmChargingWithoutCartError();
        }
    }
}
