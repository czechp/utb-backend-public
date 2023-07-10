package app.web.utb.cart.infrastructure.exception;

import app.web.utb.exception.ElementNotFoundException;

public class CartExceptionFactory {
    public static  ElementNotFoundException notExists(long id) {
        return new ElementNotFoundException(String.format("Wózek z id: %d nie istnieje", id));
    }
}
