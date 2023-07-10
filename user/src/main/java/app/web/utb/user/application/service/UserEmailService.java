package app.web.utb.user.application.service;

public interface UserEmailService {
    void sendConfirmationTokenToUser(String emailAddress, String confirmationToken);
    void sendRestorePasswordToken(String emailAddress, String restorePasswordToken);

}
