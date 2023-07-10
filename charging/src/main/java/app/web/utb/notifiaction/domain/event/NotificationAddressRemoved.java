package app.web.utb.notifiaction.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.Value;

@Value
public class NotificationAddressRemoved extends DomainEvent {
    long notificationAddressId;
}
