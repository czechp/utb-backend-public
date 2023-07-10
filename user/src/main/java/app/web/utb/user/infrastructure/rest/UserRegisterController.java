package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.application.command.UserRegisterCmd;
import app.web.utb.user.application.handler.UserRegisterCmdHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/register")
@AllArgsConstructor
@Validated
class UserRegisterController {
    private final UserRegisterCmdHandler userRegisterCmdHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void registerUser(@RequestBody @Valid UserRegisterCmd userRegisterCmd) {
        userRegisterCmdHandler.registerUser(userRegisterCmd);
    }
}
