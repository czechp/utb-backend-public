package app.web.utb.user.domain;

import app.web.utb.domainDrivenDesign.annotation.ValueObject;
import app.web.utb.validator.CommonValidators;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.PersistenceConstructor;

@ValueObject
@Getter(AccessLevel.PACKAGE)
@EqualsAndHashCode
@Builder(access = AccessLevel.PACKAGE, setterPrefix = "with")
class UserInfo {
    private String username;
    private String password;
    private String email;
    private UserRole role = UserRole.USER;

    UserInfo(String username, String password, String email) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setUserRole(UserRole.USER);
    }


    @PersistenceConstructor
    UserInfo(String username, String password, String email, UserRole userRole) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setUserRole(userRole);
    }

    private void setUserRole(UserRole userRole) {
        CommonValidators.validateNotNull(userRole, "Rola użytkownika");
        this.role = userRole;
    }

    private void setEmail(String email) {
        CommonValidators.validateEmail(email);
        this.email = email;
        this.role = UserRole.USER;
    }

    private void setPassword(String password) {
        CommonValidators.validateMinLength(password, UserConstraints.PASSWORD_MIN_LENGTH, "email");
        this.password = password;
    }

    private void setUsername(String username) {
        CommonValidators.validateMinLength(username, UserConstraints.USERNAME_MIN_LENGTH, "login");
        this.username = username;
    }

    UserInfo assignRole(UserRole newRole) {
        CommonValidators.validateNotNull(newRole, "Rola użytkownika");
        return UserInfo.builder()
                .withUsername(username)
                .withPassword(password)
                .withEmail(email)
                .withRole(newRole)
                .build();
    }

    public UserInfo assignNewPassword(String password) {
        return UserInfo.builder()
                .withUsername(username)
                .withPassword(password)
                .withEmail(email)
                .withRole(role)
                .build();
    }
}
