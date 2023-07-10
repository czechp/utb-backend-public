package app.web.utb.cart.application.command;

import app.web.utb.cart.domain.Cart;
import app.web.utb.cart.domain.CartRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CartAssignDescriptionHandler implements CmdHandler<CartAssignDescriptionCmd> {
    private final CartRepository cartRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(CartAssignDescriptionCmd command) {
        Cart cart = this.cartRepository.findByIdOrException(command.getCartId());
        cart.assignDescription(command.getDescription());
        this.domainEventPublisher.publish(cart);
    }
}
