package app.web.utb.user.infrastructure.rest;


import app.web.utb.user.application.command.UserRemoveCmd;
import app.web.utb.user.application.handler.UserRemoveCmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
class UserRemoveController {
    private final UserRemoveCmdHandler userRemoveCmdHandler;

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN"})
    void removeUser(@PathVariable(name = "userId") long userId) {
        userRemoveCmdHandler.removeUser(new UserRemoveCmd(userId));
    }
}
