package app.web.utb.user.infrastructure.email;

import app.web.utb.user.application.service.UserEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
@Profile({"development", "test"})
class UserEmailServiceDevImpl implements UserEmailService {
    @Override
    public void sendConfirmationTokenToUser(String email, String confirmationToken) {
        log.atInfo().log("Email: {} Confirmation token: {}", email, confirmationToken);
    }

    @Override
    public void sendRestorePasswordToken(String emailAddress, String restorePasswordToken) {
        log.atInfo().log("Email: {} Restore password token: {}", emailAddress, restorePasswordToken);
    }

}
