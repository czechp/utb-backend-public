package app.web.utb.cart.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class CartRemoved extends DomainEvent {
    long cartId;
}
