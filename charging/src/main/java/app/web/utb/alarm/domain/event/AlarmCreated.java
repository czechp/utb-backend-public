package app.web.utb.alarm.domain.event;

import app.web.utb.alarm.domain.AlarmType;
import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.Value;

@Value
public class AlarmCreated extends DomainEvent {
    String uuid;
    long chargingSystemId;
    int chargerPosition;
    AlarmType alarmType;
}
