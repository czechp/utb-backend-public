package app.web.utb.charging.infrastructure.listener;

import app.web.utb.charging.application.command.ChargingMarkAsFailedCmd;
import app.web.utb.charging.application.command.ChargingMarkAsFailedHandler;
import app.web.utb.chargingSystem.domain.event.ChargingInterrupted;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ChargingFailedListener {
    private final ChargingMarkAsFailedHandler chargingMarkAsFailedHandler;

    @EventListener
    public void handleChargingFailed(ChargingInterrupted chargingInterrupted) {
        ChargingMarkAsFailedCmd command = new ChargingMarkAsFailedCmd(chargingInterrupted.getCartId());
        this.chargingMarkAsFailedHandler.handle(command);
    }
}
