package app.web.utb.chargingSystem.application.command;

import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ChargingSystemUpdateDataHandler implements CmdHandler<ChargingSystemUpdateDataCmd> {
    private final ChargingSystemRepository chargingSystemRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingSystemUpdateDataCmd command) {
        ChargingSystem chargingSystem = chargingSystemRepository.findByIdOrThrowException(command.getSystemId());
        chargingSystem.updateData(command.getChargersInfo());
        domainEventPublisher.publish(chargingSystem);
    }
}
