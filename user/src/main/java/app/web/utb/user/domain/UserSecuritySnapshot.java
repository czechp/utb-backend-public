package app.web.utb.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSecuritySnapshot {
    private final String username;
    private final String password;
    private final String role;
    private String email;
    private boolean enabled;
}
