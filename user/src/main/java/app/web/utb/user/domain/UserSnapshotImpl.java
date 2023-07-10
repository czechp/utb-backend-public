package app.web.utb.user.domain;

import java.time.LocalDateTime;

public class UserSnapshotImpl implements UserSnapshot {
    private final User user;

    public UserSnapshotImpl(User user) {
        this.user = user;
    }

    @Override
    public long getId() {
        return user.getId();
    }

    @Override
    public long getVersion() {
        return user.getVersion();
    }

    @Override
    public String getUuid() {
        return user.getUuid();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return user.getCreatedAt();
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return user.getUpdatedAt();
    }

    @Override
    public String getUsername() {
        return user.getUserInfo().getUsername();
    }

    @Override
    public String getEmail() {
        return user.getUserInfo().getEmail();
    }

    @Override
    public String getPassword() {
        return user.getUserInfo().getPassword();
    }

    @Override
    public String getConfirmToken() {
        return user.getUserConfirmToken().getToken();
    }

    @Override
    public LocalDateTime getConfirmTokenExpiredAt() {
        return user.getUserConfirmToken().getExpiredAt();
    }

    @Override
    public boolean isConfirmed() {
        return user.getUserActive().isConfirmed();
    }

    @Override
    public UserRole getUserRole() {
        return user.getUserInfo().getRole();
    }

    @Override
    public boolean isConfirmedByAdmin() {
        return user.getUserActive().isConfirmedByAdmin();
    }

    @Override
    public String getRestorePasswordToken() {
        return user.getUserRestorePassword().getRestorePasswordToken();
    }

    @Override
    public LocalDateTime getRestorePasswordTokenExpiration() {
        return user.getUserRestorePassword().getRestorePasswordTokenExpiration();
    }
}
