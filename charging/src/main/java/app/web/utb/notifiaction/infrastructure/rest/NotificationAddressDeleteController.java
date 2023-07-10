package app.web.utb.notifiaction.infrastructure.rest;

import app.web.utb.notifiaction.application.command.NotificationAddressDeleteCmd;
import app.web.utb.notifiaction.application.command.NotificationAddressDeleteHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(NotificationAddressDeleteController.URL)
@AllArgsConstructor
class NotificationAddressDeleteController {
    static final String URL = "/api/notifications";

    private final NotificationAddressDeleteHandler handler;

    @DeleteMapping("/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNotificationAddressById(@PathVariable(name = "notificationId") long notificationId) {
        this.handler.handle(new NotificationAddressDeleteCmd(notificationId));
    }
}
