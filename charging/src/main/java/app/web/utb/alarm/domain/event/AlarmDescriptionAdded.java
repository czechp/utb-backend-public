package app.web.utb.alarm.domain.event;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import lombok.Value;

@Value
public class AlarmDescriptionAdded extends DomainEvent {
    long alarmId;
    String description;
}
