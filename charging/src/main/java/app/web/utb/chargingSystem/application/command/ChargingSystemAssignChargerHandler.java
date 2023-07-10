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
public class ChargingSystemAssignChargerHandler implements CmdHandler<ChargingSystemAssignChargerCmd> {
    private final ChargingSystemRepository chargingSystemRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingSystemAssignChargerCmd command) {
        ChargingSystem chargingSystem = chargingSystemRepository.findByIdOrThrowException(command.getChargingSystemId());
        chargingSystem.assignCharger(command.getChargerPosition());
        domainEventPublisher.publish(chargingSystem);
    }
}
