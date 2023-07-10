package app.web.utb.charging.infrastructure.listener;

import app.web.utb.charging.application.command.ChargingFinishCmd;
import app.web.utb.charging.application.command.ChargingFinishedHandler;
import app.web.utb.chargingSystem.domain.event.ChargingStopped;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ChargingStoppedListener {
    private final ChargingFinishedHandler chargingFinishedHandler;

    @EventListener
    public void finishCharging(ChargingStopped chargingStopped) {
        ChargingFinishCmd cmd = new ChargingFinishCmd(chargingStopped.getCartId());
        this.chargingFinishedHandler.handle(cmd);
    }
}
