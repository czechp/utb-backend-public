package app.web.utb.user.infrastructure.rest;

import app.web.utb.user.application.command.UserAssignRoleCmd;
import app.web.utb.user.application.handler.UserAssignRoleCmdHandler;
import app.web.utb.user.domain.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/assign-role")
@AllArgsConstructor
class UserAssignRoleController {
    private final UserAssignRoleCmdHandler userAssignRoleCmdHandler;

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN"})
    void assignRole(
            @PathVariable(name = "userId") long userId,
            @RequestParam(name = "role") UserRole userRole
    ) {
        UserAssignRoleCmd userAssignRoleCmd = new UserAssignRoleCmd(userId, userRole);
        userAssignRoleCmdHandler.assignRole(userAssignRoleCmd);
    }
}
