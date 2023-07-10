package app.web.utb.charging.application.command;

import app.web.utb.charging.domain.ChargingRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ChargingFinishedHandler implements CmdHandler<ChargingFinishCmd> {
    private final ChargingRepository chargingRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(ChargingFinishCmd command) {
        this.chargingRepository.findUnfinishedChargings(command.getCartId())
                .forEach(charging -> {
                    charging.finish();
                    this.domainEventPublisher.publish(charging);
                });
    }
}
