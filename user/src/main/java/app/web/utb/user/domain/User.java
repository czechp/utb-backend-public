package app.web.utb.user.domain;

import app.web.utb.domainDrivenDesign.annotation.AggregateRoot;
import app.web.utb.user.event.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Objects;

@AggregateRoot
@Builder(access = AccessLevel.PACKAGE, setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
public class User extends app.web.utb.domainDrivenDesign.superClass.AggregateRoot {
    private UserInfo userInfo;
    private UserConfirmToken userConfirmToken;
    private UserActive userActive;
    private UserRestorePassword userRestorePassword;

    User(UserInfo userInfo) {
        super();
        this.userInfo = userInfo;
        this.userConfirmToken = UserFactory.userConfirmToken();
        this.userActive = UserFactory.userActive();
        this.userRestorePassword = UserFactory.userRestorePassword();
    }

    @PersistenceConstructor
    User(long id,
         long version,
         String uuid,
         LocalDateTime createdAt,
         LocalDateTime updatedAt,
         UserInfo userInfo,
         UserConfirmToken userConfirmToken,
         UserActive userActive,
         UserRestorePassword userRestorePassword
    ) {
        super(id, version, uuid, createdAt, updatedAt);
        this.userInfo = userInfo;
        this.userConfirmToken = userConfirmToken;
        this.userActive = userActive;
        this.userRestorePassword = userRestorePassword;
    }


    UserRegistered userRegisteredEvent() {
        return new UserRegistered(super.getId(), this.userInfo.getUsername(), this.userInfo.getEmail(), this.userConfirmToken.getToken());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return super.getId() == user.getId() && this.userInfo.getUsername().equals(user.getUserInfo().getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), this.userInfo.getUsername());
    }

    @Override
    public UserSnapshot getSnapshot() {
        return UserFactory.userSnapshot(this);
    }

    public UserConfirmed confirmUser(String confirmationToken) {
        userConfirmToken.confirmToken(confirmationToken);
        this.userActive = userActive.confirmUser();
        this.userConfirmToken = UserFactory.userConfirmToken();
        return new UserConfirmed(confirmationToken);
    }

    public UserSecuritySnapshot userSecuritySnapshot() {
        return new UserSecuritySnapshot(userInfo.getUsername(), userInfo.getPassword(), userInfo.getRole().toString(), userInfo.getEmail(), userActive.isActive());
    }

    public UserRemoved userRemovedEvent() {
        return new UserRemoved(this.getId());
    }

    public UserConfirmedByAdmin confirmUserByAdmin(boolean targetState) {
        this.userActive = userActive.confirmByAdmin(targetState);
        return new UserConfirmedByAdmin(userActive.isConfirmedByAdmin());
    }

    public UserRoleAssigned assignRole(UserRole newRole) {
        this.userInfo = userInfo.assignRole(newRole);
        return new UserRoleAssigned(this.userInfo.getRole().toString());
    }

    public UserRestorePasswordTokenGenerated generateRestorePasswordToken() {
        this.userRestorePassword = UserFactory.userRestorePassword();
        return new UserRestorePasswordTokenGenerated(this.userRestorePassword.getRestorePasswordToken());
    }

    public UserNewPasswordSet setNewPassword(String token, String rawPassword, PasswordEncoder passwordEncoder) {
        this.userRestorePassword.tokenMatchOrThrowException(token);
        String newPasswordHash = passwordEncoder.encode(rawPassword);
        this.userInfo = userInfo.assignNewPassword(newPasswordHash);
        return new UserNewPasswordSet(this.userInfo.getPassword());
    }
}
