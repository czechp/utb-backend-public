package app.web.utb.user.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserTestFactory {
    public static User getUserForRegisterCmdHandler(String username, String password, String email) {
        return User.builder()
                .withUserInfo(UserFactory.userInfo(username, password, email))
                .withUserConfirmToken(UserFactory.userConfirmToken())
                .build();
    }

    public static User getUserForConfirmCmdHandler(String confirmationToken) {
        return User.builder()
                .withUserConfirmToken(new UserConfirmToken(confirmationToken, LocalDateTime.now().plus(UserConstraints.EXPIRATION_TOKEN_TIME)))
                .withUserActive(new UserActive())
                .build();
    }

    public static User getUserForRemoveCmdHandler() {
        return User.builder()
                .withUserActive(UserFactory.userActive())
                .withUserInfo(UserFactory.userInfo("Some username", "Some password", "SomeEmail@gmail.com"))
                .withUserConfirmToken(UserFactory.userConfirmToken())
                .build();
    }

    public static User getUserForConfirmByAdminCmdHandler() {
        return User.builder()
                .withUserActive(UserActive.builder().build())
                .build();
    }

    public static User getUserForAssignRoleCmdHandler() {
        return User.builder()
                .withUserInfo(new UserInfo("Some name", "Some passwrod", "Someemil@gmail.com", UserRole.USER))
                .build();
    }

    public static User getUserGenerateRestorePasswordTokenCmdHandler() {
        return User.builder()
                .withUserRestorePassword(new UserRestorePassword())
                .build();
    }

    public static User getUserForSetNewPassword(String token) {
        UserRestorePassword userRestorePassword = UserRestorePassword.builder()
                .withRestorePasswordTokenExpiration(LocalDateTime.now().plusMinutes(10))
                .withRestorePasswordToken(token)
                .build();

        UserInfo userInfo = UserInfo.builder()
                .withUsername(UUID.randomUUID().toString())
                .withPassword(UUID.randomUUID().toString())
                .withEmail("someEmail@gmail.com")
                .withRole(UserRole.USER)
                .build();

        return User.builder()
                .withUserRestorePassword(userRestorePassword)
                .withUserInfo(userInfo)
                .build();
    }
}
