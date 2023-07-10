package app.web.utb.cart.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class CartCreated extends DomainEvent {
    long cartId;
    String name;
    String description;
}
