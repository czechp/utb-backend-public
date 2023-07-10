package app.web.utb.alarm.infrastructure.rest;

import app.web.utb.alarm.application.command.AlarmRemoveCmd;
import app.web.utb.alarm.application.command.AlarmRemoveHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AlarmRemoveController.URL)
@AllArgsConstructor
class AlarmRemoveController {
    static final String URL = "/api/alarms";
    private final AlarmRemoveHandler alarmRemoveHandler;

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_MANAGEMENT", "ROLE_ADMIN"})
    void deleteAlarm(@RequestBody AlarmRemoveCmd command) {
        this.alarmRemoveHandler.handle(command);
    }
}
