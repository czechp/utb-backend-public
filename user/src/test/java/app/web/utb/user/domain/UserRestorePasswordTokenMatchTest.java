package app.web.utb.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRestorePasswordTokenMatchTest {
    @Test
    @DisplayName("UserRestorePassword.matchToken - ok")
    void tokenMatchTest() {
        //given
        String token = UUID.randomUUID().toString();
        UserRestorePassword userRestorePassword = UserRestorePassword.builder()
                .withRestorePasswordToken(token)
                .withRestorePasswordTokenExpiration(LocalDateTime.now().plusMinutes(10))
                .build();
        //when
        boolean tokenMatch = userRestorePassword.tokenMatchOrThrowException(token);
        //then
        assertTrue(tokenMatch);
    }

    @Test
    @DisplayName("UserRestorePassword.matchToken - nok token expired")
    void tokenMatchExpiredTest() {
        //given
        String token = UUID.randomUUID().toString();
        UserRestorePassword userRestorePassword = UserRestorePassword.builder()
                .withRestorePasswordToken(token)
                .withRestorePasswordTokenExpiration(LocalDateTime.now().minusMinutes(10))
                .build();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> userRestorePassword.tokenMatchOrThrowException(token));
    }

    @Test
    @DisplayName("UserRestorePassword.matchToken - nok token not match")
    void tokenNotMatchTest() {
        //given
        String token = UUID.randomUUID().toString();
        UserRestorePassword userRestorePassword = UserRestorePassword.builder()
                .withRestorePasswordToken("My_toKen_")
                .withRestorePasswordTokenExpiration(LocalDateTime.now().plusMinutes(10))
                .build();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> userRestorePassword.tokenMatchOrThrowException(token));
    }

}