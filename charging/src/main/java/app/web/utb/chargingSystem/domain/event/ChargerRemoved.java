package app.web.utb.chargingSystem.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ChargerRemoved extends DomainEvent {
    private long chargingSystemId;
    private int chargerPosition;
}
