package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.application.handler.UserConfirmCmdHandler;
import app.web.utb.user.application.command.UserConfirmCmd;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/confirm")
@Validated
@AllArgsConstructor
class UserConfirmController {
    private final UserConfirmCmdHandler userConfirmCmdHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void confirmUser(@RequestBody @Valid UserConfirmCmd userConfirmCmd) {
        userConfirmCmdHandler.confirmUser(userConfirmCmd);
    }
}
