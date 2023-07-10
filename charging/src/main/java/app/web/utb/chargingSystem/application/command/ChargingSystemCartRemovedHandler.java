package app.web.utb.chargingSystem.application.command;

import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ChargingSystemCartRemovedHandler implements CmdHandler<ChargingSystemCartRemovedCmd> {
    private final ChargingSystemRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingSystemCartRemovedCmd command) {
        List<ChargingSystem> systemsToUpdate = repository.findByChargersAssignedCarts(command.getCartId());
        systemsToUpdate
                .forEach(system -> {
                    system.cartRemoved(command.getCartId());
                    domainEventPublisher.publish(system);
                });
    }
}
