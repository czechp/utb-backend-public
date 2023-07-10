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
public class ChargingSystemRemoveChargerHandler implements CmdHandler<ChargingSystemRemoveChargerCmd> {
    private final ChargingSystemRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingSystemRemoveChargerCmd command) {
        ChargingSystem chargingSystem = repository.findByIdOrThrowException(command.getChargingSystemId());
        chargingSystem.removeCharger(command.getChargerPosition());
        this.domainEventPublisher.publish(chargingSystem);
        repository.saveAngGetId(chargingSystem);
    }
}
