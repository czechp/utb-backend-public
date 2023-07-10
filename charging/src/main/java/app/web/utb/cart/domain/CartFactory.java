package app.web.utb.cart.domain;

public class CartFactory {
    public static Cart create(String umupNumber, String description) {
        return new CartImpl(umupNumber, description);
    }
}
