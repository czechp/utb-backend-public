package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.application.command.UserConfirmByAdminCmd;
import app.web.utb.user.application.handler.UserConfirmByAdminCmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/confirm-by-admin")
@AllArgsConstructor
class UserConfirmByAdminController {
    private final UserConfirmByAdminCmdHandler userConfirmByAdminCmdHandler;

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN"})
    void confirmByAdmin(
            @PathVariable(name = "userId") long userId,
            @RequestParam(name = "activation") boolean activation
    ) {
        UserConfirmByAdminCmd userConfirmByAdminCmd = new UserConfirmByAdminCmd(userId, activation);
        userConfirmByAdminCmdHandler.confirmByAdmin(userConfirmByAdminCmd);
    }
}
