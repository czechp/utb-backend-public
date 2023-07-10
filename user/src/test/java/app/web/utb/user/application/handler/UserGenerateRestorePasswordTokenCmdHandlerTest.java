package app.web.utb.user.application.handler;

import app.web.utb.user.event.UserRestorePasswordTokenGenerated;
import app.web.utb.exception.ElementNotFoundException;
import app.web.utb.user.application.command.UserGenerateRestorePasswordTokenCmd;
import app.web.utb.user.application.service.UserEmailService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UserGenerateRestorePasswordTokenCmdHandlerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserEmailService userEmailService;

    UserGenerateRestorePasswordTokenCmdHandler cmdHandler;

    @BeforeEach
    void init() {
        this.cmdHandler = new UserGenerateRestorePasswordTokenCmdHandler(userRepository, userEmailService);
    }

    @Test
    @DisplayName("UserGenerateRestorePasswordTokenCmdHandler.generateRestorePasswordToken - ok")
    void generateRestorePasswordTokenTest() {
        //given
        String email = "someEmail@gmail.com";
        UserGenerateRestorePasswordTokenCmd cmd = new UserGenerateRestorePasswordTokenCmd(email);
        User userToRestorePassword = UserTestFactory.getUserGenerateRestorePasswordTokenCmdHandler();
        //when
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userToRestorePassword));
        UserRestorePasswordTokenGenerated tokenGenerated = cmdHandler.generateRestorePasswordToken(cmd);
        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        Mockito.verify(userEmailService, Mockito.times(1)).sendRestorePasswordToken(anyString(), anyString());

    }

    @Test
    @DisplayName("UserGenerateRestorePasswordTokenCmdHandler.generateRestorePasswordToken - nok not found")
    void generateRestorePasswordTokenNotFoundTest() {
        //given
        String email = "someEmail@gmail.com";
        UserGenerateRestorePasswordTokenCmd cmd = new UserGenerateRestorePasswordTokenCmd(email);
        User userToRestorePassword = UserTestFactory.getUserGenerateRestorePasswordTokenCmdHandler();
        //when
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(ElementNotFoundException.class, ()->cmdHandler.generateRestorePasswordToken(cmd));
    }
}