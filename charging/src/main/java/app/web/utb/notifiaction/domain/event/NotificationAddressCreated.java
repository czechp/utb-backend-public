package app.web.utb.notifiaction.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class NotificationAddressCreated extends DomainEvent {
    String uuid;
    long chargingSystemId;
    String email;
}
