package app.web.utb.alarm.application.service;

public interface AlarmEmailNotifier {
    void notifyAboutNewAlarm(long chargingSystemId, int chargerPosition);
}
