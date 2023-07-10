package app.web.utb.user.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSetNewPasswordCmd {
    private final String token;
    private final String newPassword;
}
