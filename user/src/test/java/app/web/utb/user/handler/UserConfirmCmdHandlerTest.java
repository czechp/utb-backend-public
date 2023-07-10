package app.web.utb.user.handler;

import app.web.utb.user.event.UserConfirmed;
import app.web.utb.user.application.handler.UserConfirmCmdHandler;
import app.web.utb.user.application.command.UserConfirmCmd;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith({MockitoExtension.class})
class UserConfirmCmdHandlerTest {

    @Mock
    UserRepository userRepository;

    UserConfirmCmdHandler userConfirmCmdHandler;

    @BeforeEach
    void init() {
        this.userConfirmCmdHandler = new UserConfirmCmdHandler(userRepository);
    }

    @Test
    @DisplayName("User confirm command handler - ok")
    void userConfirmCommandHandlerTest() {
        //given
        String confirmationToken = UUID.randomUUID().toString();
        UserConfirmCmd userConfirmCmd = new UserConfirmCmd(confirmationToken);
        //when
        Mockito.when(userRepository.findByConfirmationToken(anyString())).thenReturn(Optional.of(UserTestFactory.getUserForConfirmCmdHandler(confirmationToken)));
        UserConfirmed userConfirmed = userConfirmCmdHandler.confirmUser(userConfirmCmd);
        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("User confirm command handler - nok not found")
    void userConfirmCommandHandlerTestNotFound() {
        //given
        String confirmationToken = UUID.randomUUID().toString();
        UserConfirmCmd userConfirmCmd = new UserConfirmCmd(confirmationToken);
        //when
        Mockito.when(userRepository.findByConfirmationToken(anyString())).thenReturn(Optional.empty());
        //then
        assertThrows(IllegalStateException.class, () -> userConfirmCmdHandler.confirmUser(userConfirmCmd));
    }


    @Test
    @DisplayName("User confirm command handler - nok token not match")
    void userConfirmCommandHandlerTestTokenNotMatch() {
        //given
        String confirmationToken = UUID.randomUUID().toString();
        UserConfirmCmd userConfirmCmd = new UserConfirmCmd(confirmationToken);
        //when
        Mockito.when(userRepository.findByConfirmationToken(anyString())).thenReturn(Optional.of(UserTestFactory.getUserForConfirmCmdHandler(UUID.randomUUID().toString())));
        //then
        assertThrows(IllegalStateException.class, () -> userConfirmCmdHandler.confirmUser(userConfirmCmd));

    }

}