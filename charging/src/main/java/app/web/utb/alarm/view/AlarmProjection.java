package app.web.utb.alarm.view;


import java.time.LocalDateTime;

interface AlarmProjection {
    long getId();

    LocalDateTime getCreatedAt();

    int getChargerPosition();

    String getAlarmType();

    boolean isConfirmed();

    String getDescription();

    String getChargingSystemName();
}
