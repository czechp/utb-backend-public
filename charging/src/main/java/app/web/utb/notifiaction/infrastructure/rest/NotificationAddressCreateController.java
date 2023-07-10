package app.web.utb.notifiaction.infrastructure.rest;

import app.web.utb.notifiaction.application.command.NotificationAddressCreateCmd;
import app.web.utb.notifiaction.application.command.NotificationAddressCreateHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(NotificationAddressCreateController.URL)
@Validated
@AllArgsConstructor
class NotificationAddressCreateController {
    static final String URL = "/api/notifications";
    private final NotificationAddressCreateHandler createHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN"})
    void createNotificationAddress(@RequestBody @Valid NotificationAddressCreateCmd command) {
        this.createHandler.handle(command);
    }
}
