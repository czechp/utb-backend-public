package app.web.utb.chargingSystem.application.command;

import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemFactory;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import app.web.utb.chargingSystem.domain.event.ChargingSystemCreated;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ChargingSystemCreateHandler implements CmdHandler<ChargingSystemCreateCmd> {
    private final ChargingSystemRepository chargingSystemRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingSystemCreateCmd command) {
        if (chargingSystemRepository.existsByName(command.getName()))
            throw new IllegalStateException(String.format("System z nazwą %s już istnieje", command.getName()));

        if (chargingSystemRepository.existsByPlcAddressAddress(command.getPlcAddress()))
            throw new IllegalStateException(String.format("System z adresem %s już istnieje", command.getPlcAddress()));

        ChargingSystem chargingSystem = ChargingSystemFactory.create(command.getName(), command.getPlcAddress(), command.getNetworkMask());
        long id = chargingSystemRepository.saveAngGetId(chargingSystem);
        domainEventPublisher.publish(new ChargingSystemCreated(id, command.getName(), command.getPlcAddress(), command.getNetworkMask()));
    }

}
