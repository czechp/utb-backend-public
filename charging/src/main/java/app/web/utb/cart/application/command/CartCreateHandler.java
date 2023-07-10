package app.web.utb.cart.application.command;

import app.web.utb.cart.domain.CartFactory;
import app.web.utb.cart.domain.CartRepository;
import app.web.utb.cart.domain.event.CartCreated;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class CartCreateHandler implements CmdHandler<CartCreateCmd> {
    private final CartRepository cartRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(CartCreateCmd command) {
        if (cartRepository.existsByUmupNumber(command.getUmupNumber()))
            throw new IllegalStateException(String.format("Wózek z numerem: %s już istnieje", command.getUmupNumber()));
        long cartId = cartRepository.saveCart(CartFactory.create(command.getUmupNumber(), command.getDescription()));
        this.domainEventPublisher.publish(new CartCreated(cartId, command.getUmupNumber(), command.getDescription()));
    }
}
