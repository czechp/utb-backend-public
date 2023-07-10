package app.web.utb.charging.infrastructure.listener;

import app.web.utb.charging.application.command.ChargingCreateCmd;
import app.web.utb.charging.application.command.ChargingCreateHandler;
import app.web.utb.chargingSystem.domain.event.ChargingStarted;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ChargingStartedListener {
    private final ChargingCreateHandler chargingCreateHandler;

    @EventListener
    public void chargingStarted(ChargingStarted chargingStarted) {
        this.chargingCreateHandler.handle(new ChargingCreateCmd(chargingStarted.getSystemId(), chargingStarted.getChargerPosition(), chargingStarted.getCartId()));
    }
}
