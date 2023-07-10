package app.web.utb.user.domain;

import app.web.utb.user.event.UserNewPasswordSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserSetNewPasswordTest {
    PasswordEncoder passwordEncoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return UUID.randomUUID().toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return false;
        }
    };

    @Test
    @DisplayName("User.setNewPassword() - ok")
    void setNewPasswordTest() {
        //given
        String token = "my_token";
        String rawPassword = "my_password";
        String oldPassword = "old_my_password";
        UserRestorePassword userRestorePassword = UserRestorePassword.builder()
                .withRestorePasswordToken(token)
                .withRestorePasswordTokenExpiration(LocalDateTime.now().plusMinutes(10))
                .build();
        UserInfo userInfo = UserInfo.builder()
                .withPassword(oldPassword)
                .withUsername("Some username")
                .withEmail("myEmail@gmail.com")
                .withRole(UserRole.USER)
                .build();
        User user = User.builder()
                .withUserRestorePassword(userRestorePassword)
                .withUserInfo(userInfo)
                .build();
        //when
        UserNewPasswordSet userNewPasswordSet = user.setNewPassword(token, rawPassword, passwordEncoder);
        //then
        assertNotEquals(oldPassword, userNewPasswordSet.getNewPasswordHash());
    }

}