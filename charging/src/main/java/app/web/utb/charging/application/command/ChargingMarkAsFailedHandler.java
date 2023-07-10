package app.web.utb.charging.application.command;

import app.web.utb.charging.domain.ChargingRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class ChargingMarkAsFailedHandler implements CmdHandler<ChargingMarkAsFailedCmd> {
    private final ChargingRepository chargingRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingMarkAsFailedCmd command) {
        this.chargingRepository.findUnfinishedChargings(command.getCartId())
                .forEach(charging -> {
                    charging.markAsFailed();
                    this.domainEventPublisher.publish(charging);
                });
    }
}
