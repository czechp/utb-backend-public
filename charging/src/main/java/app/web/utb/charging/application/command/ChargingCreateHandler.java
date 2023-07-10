package app.web.utb.charging.application.command;

import app.web.utb.charging.domain.Charging;
import app.web.utb.charging.domain.ChargingFactory;
import app.web.utb.charging.domain.ChargingRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChargingCreateHandler implements CmdHandler<ChargingCreateCmd> {
    private final ChargingRepository chargingRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingCreateCmd command) {
        Charging charging = ChargingFactory.create(command.getSystemId(), command.getChargerPosition(), command.getCartId());
        Charging savedCharging = this.chargingRepository.save(charging);
        savedCharging.createdEvent();
        this.domainEventPublisher.publish(savedCharging);
    }
}
