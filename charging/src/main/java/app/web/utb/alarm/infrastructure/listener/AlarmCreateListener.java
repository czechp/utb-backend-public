package app.web.utb.alarm.infrastructure.listener;

import app.web.utb.alarm.application.command.AlarmCreateCmd;
import app.web.utb.alarm.application.command.AlarmCreateHandler;
import app.web.utb.alarm.domain.AlarmType;
import app.web.utb.chargingSystem.domain.event.CartNotMatchedToCharger;
import app.web.utb.chargingSystem.domain.event.ChargingInterrupted;
import app.web.utb.chargingSystem.domain.event.ChargingWithoutCartDetected;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AlarmCreateListener {
    private final AlarmCreateHandler handler;

    @EventListener
    public void createChargingInterruptionAlarm(ChargingInterrupted event) {
        AlarmCreateCmd alarmCreateCmd = new AlarmCreateCmd(event.getSystemId(), event.getChargerPosition(), AlarmType.CHARGING_INTERRUPTED);
        handler.handle(alarmCreateCmd);
    }

    @EventListener
    public void createCartNotMatchAlarm(CartNotMatchedToCharger event) {
        AlarmCreateCmd alarmCreateCmd = new AlarmCreateCmd(event.getSystemId(), event.getChargerPosition(), AlarmType.CART_NOT_MATCH);
        handler.handle(alarmCreateCmd);
    }

    @EventListener
    public void chargingWithoutCartAlarm(ChargingWithoutCartDetected event) {
        AlarmCreateCmd alarmCreateCmd = new AlarmCreateCmd(event.getChargingSystemId(), event.getChargerPosition(), AlarmType.CHARGING_WITHOUT_CART);
        handler.handle(alarmCreateCmd);
    }
}
