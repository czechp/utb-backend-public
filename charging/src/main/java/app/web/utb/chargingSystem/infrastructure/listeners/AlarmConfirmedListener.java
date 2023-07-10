package app.web.utb.chargingSystem.infrastructure.listeners;

import app.web.utb.alarm.domain.event.AlarmConfirmed;
import app.web.utb.chargingSystem.application.command.ChargingSystemAlarmConfirmedCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemAlarmConfirmedHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AlarmConfirmedListener {
    private final ChargingSystemAlarmConfirmedHandler handler;

    @EventListener
    public void alarmConfirmed(AlarmConfirmed alarmConfirmed) {
        this.handler.handle(new ChargingSystemAlarmConfirmedCmd(alarmConfirmed.getChargingSystemId(), alarmConfirmed.getChargerPosition(), alarmConfirmed.getAlarmType()));
    }
}
