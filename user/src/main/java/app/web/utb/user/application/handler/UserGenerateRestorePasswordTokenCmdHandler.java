package app.web.utb.user.application.handler;

import app.web.utb.user.event.UserRestorePasswordTokenGenerated;
import app.web.utb.exception.ElementNotFoundException;
import app.web.utb.user.application.command.UserGenerateRestorePasswordTokenCmd;
import app.web.utb.user.application.service.UserEmailService;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserGenerateRestorePasswordTokenCmdHandler {
    private final UserRepository userRepository;
    private final UserEmailService userEmailService;

    public UserRestorePasswordTokenGenerated generateRestorePasswordToken(UserGenerateRestorePasswordTokenCmd cmd) {
        User user = userRepository.findByEmail(cmd.getEmail())
                .orElseThrow(() -> new ElementNotFoundException(String.format("Użytkownik z emailem %s nie istnieje", cmd.getEmail())));
        UserRestorePasswordTokenGenerated userRestorePasswordTokenGenerated = user.generateRestorePasswordToken();
        userEmailService.sendRestorePasswordToken(cmd.getEmail(), userRestorePasswordTokenGenerated.getRestoreToken());
        userRepository.save(user);
        return userRestorePasswordTokenGenerated;
    }
}
