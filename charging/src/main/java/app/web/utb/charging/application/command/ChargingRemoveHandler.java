package app.web.utb.charging.application.command;

import app.web.utb.charging.domain.Charging;
import app.web.utb.charging.domain.ChargingRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ChargingRemoveHandler implements CmdHandler<CharingRemoveCmd> {
    private final ChargingRepository chargingRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(CharingRemoveCmd command) {
        Charging charging = this.chargingRepository.findByIdOrThrowException(command.getChargingId());
        this.chargingRepository.delete(charging);
    }
}
