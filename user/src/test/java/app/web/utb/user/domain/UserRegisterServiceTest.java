package app.web.utb.user.domain;


import app.web.utb.user.event.UserRegistered;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    UserRegisterService userRegisterService;

    @BeforeEach
    void init() {
        this.userRegisterService = UserFactory.userRegisterService(userRepository, passwordEncoder);

    }

    @Test
    @DisplayName("Register a new user - ok")
    void registerServiceTest() {
        //given
        String username = "someUsername";
        String email = "someEmail@gmail.com";
        String password = "somePassword";
        //when
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn(UUID.randomUUID().toString());
        Mockito.when(userRepository.usernameIsUnique(anyString())).thenReturn(true);
        Mockito.when(userRepository.emailIsUnique(anyString())).thenReturn(true);
        Mockito.when(userRepository.save(any())).thenReturn(User.builder()
                .withUserInfo(UserFactory.userInfo(username, password, email))
                .withUserConfirmToken(UserFactory.userConfirmToken())
                .build());

        UserRegistered userRegistered = userRegisterService.registerNewUser(
                username,
                password,
                email
        );
        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        assertEquals(username, userRegistered.getUsername());
        assertEquals(email, userRegistered.getEmail());
    }

    @Test
    @DisplayName("Register a new user - username too short")
    void registerServiceTestUsernameToShort() {
        //given
        String username = "12";
        String email = "someEmail@gmail.com";
        String password = "somePassword";
        //when
        Mockito.when(userRepository.usernameIsUnique(anyString())).thenReturn(true);
        Mockito.when(userRepository.emailIsUnique(anyString())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> userRegisterService.registerNewUser(username, password, email));
    }

    @Test
    @DisplayName("Register a new user - password too short")
    void registerServiceTestPasswordToShort() {
        //given
        String username = "Some email";
        String email = "someEmail@gmail.com";
        String password = "12";
        //when
        Mockito.when(userRepository.usernameIsUnique(anyString())).thenReturn(true);
        Mockito.when(userRepository.emailIsUnique(anyString())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> userRegisterService.registerNewUser(username, password, email));
    }

    @Test
    @DisplayName("Register a new user - email incorrect")
    void registerServiceTestEmailIncorrect() {
        //given
        String username = "Some email";
        String email = "someEmailgmail.com";
        String password = "Some password";
        //when
        Mockito.when(userRepository.usernameIsUnique(anyString())).thenReturn(true);
        Mockito.when(userRepository.emailIsUnique(anyString())).thenReturn(true);
        //then
        assertThrows(IllegalArgumentException.class, () -> userRegisterService.registerNewUser(username, password, email));
    }

    @Test
    @DisplayName("Register a new user - username not unique")
    void registerServiceTestUsernameNotUnique() {
        //given
        String username = "someUsername";
        String email = "someEmail@gmail.com";
        String password = "somePassword";
        //when
        Mockito.when(userRepository.usernameIsUnique(anyString())).thenReturn(false);
        //then
        assertThrows(IllegalStateException.class, () -> userRegisterService.registerNewUser(username, password, email));
    }

    @Test
    @DisplayName("Register a new user - email not unique")
    void registerServiceTestEmailNotUnique() {
        //given
        String username = "someUsername";
        String email = "someEmail@gmail.com";
        String password = "somePassword";
        //when
        Mockito.when(userRepository.usernameIsUnique(anyString())).thenReturn(true);
        Mockito.when(userRepository.emailIsUnique(anyString())).thenReturn(false);
        //then
        assertThrows(IllegalStateException.class, () -> userRegisterService.registerNewUser(username, password, email));
    }


}