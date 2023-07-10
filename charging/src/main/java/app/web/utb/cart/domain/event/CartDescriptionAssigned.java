package app.web.utb.cart.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.Getter;

@Getter
public class CartDescriptionAssigned extends DomainEvent {
    private final long cartId;
    private final String description;

    public CartDescriptionAssigned(long id, String description) {
        this.cartId = id;
        this.description = description;
    }
}
