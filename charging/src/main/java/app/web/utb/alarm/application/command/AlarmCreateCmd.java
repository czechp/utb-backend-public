package app.web.utb.alarm.application.command;

import app.web.utb.alarm.domain.AlarmType;
import lombok.Value;

@Value
public class AlarmCreateCmd {
    long chargingSystemId;
    int chargerPosition;
    AlarmType alarmType;
}
