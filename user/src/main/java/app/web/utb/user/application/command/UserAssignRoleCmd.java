package app.web.utb.user.application.command;

import app.web.utb.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
class UserAssignRoleCmd {
    private final long userId;
    private final UserRole userRole;
}
