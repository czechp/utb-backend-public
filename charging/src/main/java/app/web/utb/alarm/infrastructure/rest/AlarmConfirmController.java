package app.web.utb.alarm.infrastructure.rest;

import app.web.utb.alarm.application.command.AlarmConfirmCmd;
import app.web.utb.alarm.application.command.AlarmConfirmHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AlarmConfirmController.URL)
@AllArgsConstructor
class AlarmConfirmController {
    static final String URL = "/api/alarms/confirm";
    private final AlarmConfirmHandler handler;

    @PostMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void confirmAlarm(@RequestBody AlarmConfirmCmd command) {
        this.handler.handle(command);
    }
}
