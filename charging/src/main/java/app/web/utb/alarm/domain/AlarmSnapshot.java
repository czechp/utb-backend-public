package app.web.utb.alarm.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AlarmSnapshot {
    long chargingSystemId;
    int chargerPosition;
}
