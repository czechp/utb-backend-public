package app.web.utb.chargingSystem.application.command;

import app.web.utb.alarm.domain.AlarmType;
import lombok.Value;

@Value
public class ChargingSystemAlarmConfirmedCmd {
    long chargingSystemId;
    int chargerPosition;
    AlarmType alarmType;
}
