package app.web.utb.charging.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class ChargingCreated extends DomainEvent {
    long uuid;
    long systemId;
    long cartId;
    int chargerPosition;
}
