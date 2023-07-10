package app.web.utb.chargingSystem.application.command;

import app.web.utb.cart.domain.CartRepository;
import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ChargingSystemAssignCartToChargerHandler implements CmdHandler<ChargingSystemAssignCartToChargerCmd> {
    private ChargingSystemRepository chargingSystemRepository;
    private CartRepository cartRepository;
    private DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingSystemAssignCartToChargerCmd command) {
        ChargingSystem chargingSystem = chargingSystemRepository.findByIdOrThrowException(command.getSystemId());
        cartRepository.findByIdOrException(command.getCartId());
        chargingSystem.assignCart(command.getChargerPosition(), command.getCartId());
        domainEventPublisher.publish(chargingSystem);
    }
}
