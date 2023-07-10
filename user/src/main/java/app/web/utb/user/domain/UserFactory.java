package app.web.utb.user.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class UserFactory {

    static User user(String username, String password, String email) {
        UserInfo userInfo = UserFactory.userInfo(username, password, email);
        return new User(userInfo);
    }

    static User userFromPersistence(UserSnapshot snap) {
        return new User(snap.getId(),
                snap.getVersion(),
                snap.getUuid(),
                snap.getCreatedAt(),
                snap.getUpdatedAt(),
                UserFactory.userInfoFromPersistence(snap.getUsername(), snap.getPassword(), snap.getEmail(), snap.getUserRole()),
                UserFactory.userConfirmTokenFromPersistence(snap.getConfirmToken(), snap.getConfirmTokenExpiredAt()),
                UserFactory.userActiveFromPersistence(snap.isConfirmed(), snap.isConfirmedByAdmin()),
                UserFactory.userRestorePasswordFromPersistence(snap.getRestorePasswordToken(), snap.getRestorePasswordTokenExpiration())
        );
    }

    private static UserRestorePassword userRestorePasswordFromPersistence(String restorePasswordToken,
                                                                          LocalDateTime restorePasswordTokenExpiration) {
        return new UserRestorePassword(restorePasswordToken, restorePasswordTokenExpiration);
    }

    static UserSnapshot userSnapshot(User user) {
        return new UserSnapshotImpl(user);
    }

    public static UserRegisterService userRegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserRegisterService(userRepository, passwordEncoder);
    }

    static UserInfo userInfo(String username, String password, String email) {
        return new UserInfo(username, password, email);
    }

    static UserInfo userInfoFromPersistence(String username, String password, String email, UserRole userRole) {
        return new UserInfo(username, password, email, userRole);
    }

    static UserConfirmToken userConfirmToken() {
        return new UserConfirmToken();
    }

    private static UserConfirmToken userConfirmTokenFromPersistence(String token, LocalDateTime expiredAt) {
        return new UserConfirmToken(token, expiredAt);
    }


    static UserActive userActive() {
        return new UserActive();
    }

    static UserActive userActiveFromPersistence(boolean confirmed, boolean confirmedByAdmin) {
        return new UserActive(confirmed, confirmedByAdmin);
    }

    static UserRestorePassword userRestorePassword() {
        return new UserRestorePassword();
    }
}
