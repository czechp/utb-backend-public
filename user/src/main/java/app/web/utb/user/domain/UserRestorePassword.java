package app.web.utb.user.domain;

import app.web.utb.validator.CommonValidators;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter(AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE, setterPrefix = "with")
class UserRestorePassword {
    private String restorePasswordToken;
    private LocalDateTime restorePasswordTokenExpiration;

    UserRestorePassword() {
        this.restorePasswordToken = UUID.randomUUID().toString();
        this.restorePasswordTokenExpiration = LocalDateTime.now().plus(UserConstraints.EXPIRATION_TOKEN_TIME);
    }

    @PersistenceConstructor
    UserRestorePassword(String restorePasswordToken, LocalDateTime restorePasswordTokenExpiration) {
        setRestorePasswordToken(restorePasswordToken);
        setRestorePasswordTokenExpiration(restorePasswordTokenExpiration);
    }

    private void setRestorePasswordTokenExpiration(LocalDateTime restorePasswordTokenExpiration) {
        CommonValidators.validateNotNull(restorePasswordTokenExpiration, "data wygaśniecia tokenu odzyskiwania hasła");
        this.restorePasswordTokenExpiration = restorePasswordTokenExpiration;
    }

    private void setRestorePasswordToken(String restorePasswordToken) {
        CommonValidators.validateNotNull(restorePasswordToken, "token odzyskiwania hasła");
        this.restorePasswordToken = restorePasswordToken;
    }

    boolean tokenMatchOrThrowException(String token) {
        final boolean tokenMatch = this.restorePasswordToken.equals(token);
        if(!tokenMatch)
            throw new IllegalArgumentException("Niepoprawny token odzyskiwania hasła");

        final boolean tokenValid = LocalDateTime.now().isBefore(this.restorePasswordTokenExpiration);
        if(!tokenValid)
            throw new IllegalArgumentException("Token wygasł");

        return tokenMatch && tokenValid;
    }
}
