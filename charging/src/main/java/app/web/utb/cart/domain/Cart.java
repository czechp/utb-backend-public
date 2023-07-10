package app.web.utb.cart.domain;

import app.web.utb.domainDrivenDesign.interfaces.Aggregate;

public interface Cart extends Aggregate<CartSnapshot> {
    void assignDescription(String description);
}
