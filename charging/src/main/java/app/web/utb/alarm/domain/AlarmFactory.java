package app.web.utb.alarm.domain;

public class AlarmFactory {
    public static Alarm alarm(long chargingSystemId, int chargerPosition, AlarmType alarmType) {
        return new Alarm(chargingSystemId, chargerPosition, alarmType);
    }
}
