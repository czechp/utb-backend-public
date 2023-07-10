package app.web.utb.chargingSystem.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ChargingSystemCreated extends DomainEvent {
    long chargingSystemId;
    String name;
    String plcAddress;
    String networkMask;
}
