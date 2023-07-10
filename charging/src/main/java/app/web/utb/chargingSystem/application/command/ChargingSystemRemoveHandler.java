package app.web.utb.chargingSystem.application.command;

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
public class ChargingSystemRemoveHandler implements CmdHandler<ChargingSystemRemoveCmd> {
    private final ChargingSystemRepository chargingSystemRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingSystemRemoveCmd command) {
        ChargingSystem chargingSystem = this.chargingSystemRepository.findByIdOrThrowException(command.getSystemId());
        chargingSystemRepository.delete(chargingSystem);
        chargingSystem.remove();
        this.domainEventPublisher.publish(chargingSystem);
    }
}
