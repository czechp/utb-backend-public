package app.web.utb.chargingSystem.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public
class ChargingStarted extends DomainEvent {
    long systemId;
    long cartId;
    int chargerPosition;
}
