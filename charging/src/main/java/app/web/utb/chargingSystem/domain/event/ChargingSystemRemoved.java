package app.web.utb.chargingSystem.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.Value;

@Value
public class ChargingSystemRemoved extends DomainEvent {
    long systemId;
}
