package app.web.utb.user.application.handler;

import app.web.utb.user.event.UserConfirmed;
import app.web.utb.user.application.command.UserConfirmCmd;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserConfirmCmdHandler {
    private final UserRepository userRepository;

    public UserConfirmed confirmUser(UserConfirmCmd userConfirmCmd) {
        User user = userRepository.findByConfirmationToken(userConfirmCmd.getConfirmationToken())
                .orElseThrow(() -> new IllegalStateException(String
                        .format("Nie znaleziono użytkownika z takim tokenem: %s", userConfirmCmd.getConfirmationToken())));
        UserConfirmed userConfirmed = user.confirmUser(userConfirmCmd.getConfirmationToken());
        userRepository.save(user);
        return userConfirmed;
    }
}
