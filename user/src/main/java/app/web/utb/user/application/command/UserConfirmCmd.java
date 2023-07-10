package app.web.utb.user.application.command;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserConfirmCmd {
    private String confirmationToken;

    public UserConfirmCmd(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }
}
