package app.web.utb.cart.application.command;

import app.web.utb.cart.domain.Cart;
import app.web.utb.cart.domain.CartRepository;
import app.web.utb.cart.domain.event.CartRemoved;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class CartRemoveHandler implements CmdHandler<CartRemoveCmd> {
    private final CartRepository cartRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void handle(CartRemoveCmd command) {
        Cart cartToRemove = cartRepository.findByIdOrException(command.getCartId());
        this.cartRepository.delete(cartToRemove);
        eventPublisher.publishEvent(new CartRemoved(command.getCartId()));
    }
}
