package app.web.utb.user.application.handler;

import app.web.utb.user.event.UserNewPasswordSet;
import app.web.utb.user.application.command.UserSetNewPasswordCmd;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import app.web.utb.user.domain.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UserSetNewPasswordCmdHandlerTest {
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;

    UserSetNewPasswordCmdHandler userSetNewPasswordCmdHandler;


    @BeforeEach
    void init() {
        this.userSetNewPasswordCmdHandler = new UserSetNewPasswordCmdHandler(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("UserSetNewPasswordCmdHandler.setNewPassword() - ok")
    void setNewPassword() {
        //given
        String token = "my_token";
        String newPassword = "my_password";
        UserSetNewPasswordCmd userSetNewPasswordCmd = new UserSetNewPasswordCmd(token, newPassword);
        User user = UserTestFactory.getUserForSetNewPassword(token);
        //when
        Mockito.when(userRepository.findByRestorePasswordToken(anyString())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(UUID.randomUUID().toString());
        UserNewPasswordSet userNewPasswordSet = userSetNewPasswordCmdHandler.setNewPassword(userSetNewPasswordCmd);
        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

}