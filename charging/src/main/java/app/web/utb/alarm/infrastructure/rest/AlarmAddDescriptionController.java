package app.web.utb.alarm.infrastructure.rest;

import app.web.utb.alarm.application.command.AlarmAddDescriptionCmd;
import app.web.utb.alarm.application.command.AlarmAddDescriptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AlarmAddDescriptionController.URL)
@AllArgsConstructor
class AlarmAddDescriptionController {
    static final String URL = "/api/alarms/description";
    private final AlarmAddDescriptionHandler handler;

    @PostMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addDescriptionToAlarm(@RequestBody AlarmAddDescriptionCmd command) {
        this.handler.handle(command);
    }

}
