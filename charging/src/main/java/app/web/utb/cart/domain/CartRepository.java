package app.web.utb.cart.domain;

import app.web.utb.cart.infrastructure.exception.CartExceptionFactory;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<CartImpl, Long> {
    boolean existsByUmupNumber(String umupNumber);

    default long saveCart(Cart cart) {
        CartImpl savedCart = save((CartImpl) cart);
        return savedCart.getId();
    }

    default Cart findByIdOrException(long cartId) {
        return this.findById(cartId)
                .map(cart -> (Cart) cart)
                .orElseThrow(() -> CartExceptionFactory.notExists(cartId));
    }

    void delete(Cart cart);
}
