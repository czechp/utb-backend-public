package app.web.utb.user.domain;

import app.web.utb.domainDrivenDesign.annotation.DomainRepository;

import java.util.List;
import java.util.Optional;

@DomainRepository
public interface UserRepository {
    static User createUser(UserSnapshot userSnapshot) {
        return UserFactory.userFromPersistence(userSnapshot);
    }

    boolean usernameIsUnique(String username);

    boolean emailIsUnique(String email);

    User save(User user);

    Optional<User> findByConfirmationToken(String confirmationToken);

    Optional<User> findByUsername(String username);

    User findByIdOrThrowException(long userId);

    User remove(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByRestorePasswordToken(String restoreToken);

    List<User> findAllByUserInfoRole(UserRole management);
}
