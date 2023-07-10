package app.web.utb.user.domain;

import app.web.utb.domainDrivenDesign.annotation.DomainService;
import app.web.utb.user.event.UserRegistered;
import app.web.utb.validator.CommonValidators;
import org.springframework.security.crypto.password.PasswordEncoder;

@DomainService
public class UserRegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserRegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegistered registerNewUser(String username, String password, String email) {
        if (!userRepository.usernameIsUnique(username))
            throw new IllegalStateException(String.format("Użytkownik: %s już istnieje", username));

        if (!userRepository.emailIsUnique(email))
            throw new IllegalStateException(String.format("Email: %s został już zarejestrowany", email));

        CommonValidators.validateMinLength(password, UserConstraints.PASSWORD_MIN_LENGTH, "hasło");
        String encryptedPassword = passwordEncoder.encode(password);
        User user = UserFactory.user(username, encryptedPassword, email);
        User savedUser = userRepository.save(user);

        return savedUser.userRegisteredEvent();
    }
}
