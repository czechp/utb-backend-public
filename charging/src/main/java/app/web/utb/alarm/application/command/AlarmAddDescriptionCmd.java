package app.web.utb.alarm.application.command;

import lombok.Data;

@Data
public class AlarmAddDescriptionCmd {
    long alarmId;
    String description;
}
