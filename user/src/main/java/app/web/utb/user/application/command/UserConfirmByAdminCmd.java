package app.web.utb.user.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
class UserConfirmByAdminCmd {
    private final long userId;
    private final boolean targetState;
}
