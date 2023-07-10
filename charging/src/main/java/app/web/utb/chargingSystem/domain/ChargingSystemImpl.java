package app.web.utb.chargingSystem.domain;

import app.web.utb.alarm.domain.AlarmType;
import app.web.utb.chargingSystem.domain.event.*;
import app.web.utb.chargingSystem.infrastructure.persistence.ChargingSystemTableName;
import app.web.utb.domainDrivenDesign.event.DomainEvent;
import app.web.utb.domainDrivenDesign.superClassEntity.AggregateRoot;
import app.web.utb.exception.ElementNotFoundException;
import app.web.utb.validator.CommonValidators;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = ChargingSystemTableName.CHARGING_SYSTEM_TABLE_NAME)
@NoArgsConstructor
class ChargingSystemImpl extends AggregateRoot implements ChargingSystem {
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "charging_system_id")
    private final Set<Charger> chargers = new HashSet<>();

    @Length(min = ChargingSystemConstraint.NAME_MIN_LENGTH)
    @Column(unique = true)
    private String name;

    @Embedded
    private PlcAddress plcAddress;

    ChargingSystemImpl(String name, PlcAddress plcAddress) {
        this.name = name;
        this.plcAddress = plcAddress;
        validate();
    }

    @Override
    public void assignCharger(int chargerPosition) {
        if (!chargerPositionIsNotUsed(chargerPosition))
            throw new IllegalStateException(String.format("Istnieje już prostownik na pozycji %d", chargerPosition));
        if (!chargerPositionIsCorrect(chargerPosition))
            throw new IllegalArgumentException("Pozycja musi być mniejsza od 20");

        this.chargers.add(new Charger(chargerPosition));
        super.addDomainEvent(new ChargerAssigned(this.getId(), chargerPosition));
    }

    @Override
    public ChargingSystemSnapshot getSnapshot() {
        return new ChargingSystemSnapshot(
                super.getId(),
                this.plcAddress.address,
                this.name,
                this.chargers.stream()
                        .map(Charger::getPosition)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void assignCart(int chargerPosition, long cartId) {
        Charger chargerToAssign = getChargerByPosition(chargerPosition);
        chargerToAssign.assignCart(cartId);
        super.addDomainEvent(new CartAssigned(this.getId(), chargerPosition, cartId));
    }

    @Override
    public void detachCart(int chargerPosition, long cartId) {
        Charger chargerToDetachCart = getChargerByPosition(chargerPosition);
        boolean detached = chargerToDetachCart.detachCart(cartId);
        if (detached)
            super.addDomainEvent(new CartDetached(this.getId(), chargerPosition, cartId));
    }

    @Override
    public void updateData(List<ChargerDataFromController> dataFromController) {
        dataFromController.forEach(this::updateChargerData);
        super.increaseVersion();
    }

    @Override
    public void confirmError(int chargerPosition, AlarmType alarmType) {
        this.chargers.stream()
                .filter(charger -> charger.hasPosition(chargerPosition))
                .findAny()
                .ifPresent(charger -> charger.confirmError(alarmType));
    }

    @Override
    public void removeCharger(int chargerPosition) {
        Charger chargerToRemove = this.chargers.stream()
                .filter(charger -> charger.hasPosition(chargerPosition))
                .findAny()
                .orElseThrow(() -> new ElementNotFoundException(String.format("Nie istnieje prostownik na pozycji %d", chargerPosition)));

        this.chargers.remove(chargerToRemove);
        super.addDomainEvent(new ChargerRemoved(super.getId(), chargerPosition));
    }

    @Override
    public void cartRemoved(long cartId) {
        this.chargers.stream()
                .filter(charger -> charger.hasCart(cartId))
                .map(charger -> {
                    charger.detachCart(cartId);
                    return new CartDetached(this.getId(), charger.getPosition(), cartId);
                })
                .forEach(super::addDomainEvent);
    }

    @Override
    public void remove() {
        super.addDomainEvent(new ChargingSystemRemoved(this.getId()));
    }

    private void updateChargerData(ChargerDataFromController dataFromController) {
        this.chargers.stream()
                .filter(charger -> charger.hasPosition(dataFromController.getChargerPosition()))
                .forEach(charger -> {
                    List<DomainEvent> domainEvents = charger.updateData(dataFromController);
                    domainEvents.forEach(this::addDomainEvent);
                });
    }


    private Charger getChargerByPosition(int chargerPosition) {
        return this.chargers.stream()
                .filter(charger -> charger.hasPosition(chargerPosition))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(String.format("Prostownik na pozycji %d nie istnieje", chargerPosition)));
    }

    private boolean chargerPositionIsCorrect(int chargerPosition) {
        return chargerPosition <= ChargingSystemConstraint.AMOUNT_CHARGERS_IN_SYSTEM;
    }

    private boolean chargerPositionIsNotUsed(int chargerPosition) {
        return this.chargers.stream()
                .noneMatch(charger -> charger.hasPosition(chargerPosition));
    }

    private void validate() {
        CommonValidators.validateMinLength(this.name, ChargingSystemConstraint.NAME_MIN_LENGTH, "nazwa systemu");
    }
}
