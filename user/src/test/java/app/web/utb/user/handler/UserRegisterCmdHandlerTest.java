package app.web.utb.user.handler;

import app.web.utb.user.event.UserRegistered;
import app.web.utb.user.application.handler.UserRegisterCmdHandler;
import app.web.utb.user.application.command.UserRegisterCmd;
import app.web.utb.user.application.service.UserEmailService;
import app.web.utb.user.domain.UserRepository;
import app.web.utb.user.domain.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UserRegisterCmdHandlerTest {
    UserRegisterCmdHandler userRegisterCmdHandler;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @Mock
    UserEmailService userEmailService;

    @BeforeEach
    void init() {
        this.userRegisterCmdHandler = new UserRegisterCmdHandler(userRepository, passwordEncoder, applicationEventPublisher, userEmailService);
    }

    @Test
    @DisplayName("Register a new user - ok")
    void registerUserTest() {
        //given
        String username = "Some username";
        String email = "someEmail@gmail.com";
        String password = "somePassword123";
        UserRegisterCmd userRegisterCmd = new UserRegisterCmd(username, email, password);
        //when
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(UUID.randomUUID().toString());
        Mockito.when(userRepository.emailIsUnique(anyString())).thenReturn(true);
        Mockito.when(userRepository.usernameIsUnique(anyString())).thenReturn(true);
        Mockito.when(userRepository.save(any())).thenReturn(UserTestFactory
                .getUserForRegisterCmdHandler(username, password, email));
        UserRegistered userRegistered = userRegisterCmdHandler.registerUser(userRegisterCmd);
        //then
        assertEquals(username, userRegistered.getUsername());
        assertEquals(email, userRegistered.getEmail());
    }
}