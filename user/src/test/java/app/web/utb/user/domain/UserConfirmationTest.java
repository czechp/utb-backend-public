package app.web.utb.user.domain;

import app.web.utb.user.event.UserConfirmed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserConfirmationTest {

    @Test
    @DisplayName("User confirmation - ok")
    void userConfirmationTest() {
        //given
        String confirmationToken = UUID.randomUUID().toString();
        User user = User.builder()
                .withUserConfirmToken(new UserConfirmToken(confirmationToken, LocalDateTime.now().plus(UserConstraints.EXPIRATION_TOKEN_TIME)))
                .withUserActive(new UserActive())
                .build();
        //when
        UserConfirmed userConfirmed = user.confirmUser(confirmationToken);
        //then
        assertEquals(confirmationToken, userConfirmed.getConfirmationToken());
        assertTrue(user.getUserActive().isConfirmed());
    }

    @Test
    @DisplayName("User confirmation - token doesn't match")
    void userConfirmationTestTokenNotMatch() {
        //given
        String confirmationToken = UUID.randomUUID().toString();
        User user = User.builder()
                .withUserConfirmToken(new UserConfirmToken(UUID.randomUUID().toString(), LocalDateTime.now().plus(UserConstraints.EXPIRATION_TOKEN_TIME)))
                .withUserActive(new UserActive())
                .build();
        //when
        //then
        assertThrows(IllegalStateException.class, () -> user.confirmUser(confirmationToken));
    }

    @Test
    @DisplayName("User confirmation - token expired")
    void userConfirmationTestTokenExpired() {
        //given
        String confirmationToken = UUID.randomUUID().toString();
        User user = User.builder()
                .withUserConfirmToken(new UserConfirmToken(confirmationToken, LocalDateTime.now().minus(UserConstraints.EXPIRATION_TOKEN_TIME)))
                .withUserActive(new UserActive())
                .build();
        //when
        //then
        assertThrows(IllegalStateException.class, () -> user.confirmUser(confirmationToken));
    }
}