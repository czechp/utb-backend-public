package app.web.utb.alarm.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class AlarmRemoved extends DomainEvent {
    long alarmId;
}
