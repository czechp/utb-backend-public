package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.application.command.UserGenerateRestorePasswordTokenCmd;
import app.web.utb.user.application.handler.UserGenerateRestorePasswordTokenCmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/password-restore")
@AllArgsConstructor
class UserGenerateRestorePasswordTokenController {
    private final UserGenerateRestorePasswordTokenCmdHandler cmdHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void generateRestorePasswordToken(@RequestParam(name = "email") String email) {
        UserGenerateRestorePasswordTokenCmd cmd = new UserGenerateRestorePasswordTokenCmd(email);
        cmdHandler.generateRestorePasswordToken(cmd);
    }
}
