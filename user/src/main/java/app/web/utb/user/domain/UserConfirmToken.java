package app.web.utb.user.domain;

import app.web.utb.domainDrivenDesign.annotation.ValueObject;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@ValueObject
@Getter(AccessLevel.PACKAGE)
@EqualsAndHashCode
class UserConfirmToken {
    private String token;
    private LocalDateTime expiredAt;

    UserConfirmToken() {
        this.token = UUID.randomUUID().toString();
        this.expiredAt = LocalDateTime.now().plus(UserConstraints.EXPIRATION_TOKEN_TIME);
    }

    @PersistenceConstructor
    public UserConfirmToken(String token, LocalDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public void confirmToken(String confirmationToken) {
        if (!token.equals(confirmationToken))
            throw new IllegalStateException("Token nie pasuje do tego konta");
        if (LocalDateTime.now().isAfter(expiredAt))
            throw new IllegalStateException("Token wygasł");
    }
}
