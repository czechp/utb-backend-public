package app.web.utb.user.application.handler;

import app.web.utb.user.event.UserRegistered;
import app.web.utb.user.application.command.UserRegisterCmd;
import app.web.utb.user.application.service.UserEmailService;
import app.web.utb.user.domain.UserFactory;
import app.web.utb.user.domain.UserRegisterService;
import app.web.utb.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserRegisterCmdHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserEmailService emailService;


    public UserRegistered registerUser(UserRegisterCmd userRegisterCmd) {
        UserRegisterService userRegisterService = UserFactory.userRegisterService(userRepository, passwordEncoder);
        UserRegistered userRegistered = userRegisterService.registerNewUser(
                userRegisterCmd.getUsername(),
                userRegisterCmd.getPassword(),
                userRegisterCmd.getEmail()
        );
        emailService.sendConfirmationTokenToUser(userRegistered.getEmail(), userRegistered.getConfirmationToken());
        return userRegistered;
    }


}
