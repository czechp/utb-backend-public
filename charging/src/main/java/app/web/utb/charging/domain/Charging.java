package app.web.utb.charging.domain;

import app.web.utb.charging.domain.event.ChargingCreated;
import app.web.utb.charging.domain.event.ChargingFinished;
import app.web.utb.charging.domain.event.ChargingRemoved;
import app.web.utb.charging.infrastructure.persistence.ChargingTableName;
import app.web.utb.domainDrivenDesign.superClassEntity.AggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = ChargingTableName.TABLE_NAME)
@NoArgsConstructor
public class Charging extends AggregateRoot {
    private long chargingSystemId;
    private int chargerPosition;
    private long cartId;
    private LocalDateTime finishedAt;
    private boolean failed;

    Charging(long systemId, int chargerPosition, long cartId) {
        this.chargingSystemId = systemId;
        this.chargerPosition = chargerPosition;
        this.cartId = cartId;
        this.failed = false;
    }

    public void createdEvent() {
        super.addDomainEvent(new ChargingCreated(super.getId(), this.chargingSystemId, this.cartId, this.chargerPosition));
    }

    public void finish() {
        this.finishedAt = LocalDateTime.now();
        super.addDomainEvent(new ChargingFinished(super.getId()));
    }

    public void removedEvent() {
        super.addDomainEvent(new ChargingRemoved(super.getId()));
    }

    public void markAsFailed() {
        this.failed = true;
    }
}
