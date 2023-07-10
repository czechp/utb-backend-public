package app.web.utb.user.infrastructure.persistence;

import app.web.utb.exception.ElementNotFoundException;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import app.web.utb.user.domain.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public boolean usernameIsUnique(String username) {
        String query = "select distinct count(user) from UserEntity user where user.username=:username";
        Long amountOfRow = entityManager.createQuery(query, Long.class).setParameter("username", username).getSingleResult();
        return amountOfRow == 0;
    }

    @Override
    public boolean emailIsUnique(String email) {
        String query = "select distinct count(user) from UserEntity user where user.email=:email";
        Long amountOfRow = entityManager.createQuery(query, Long.class).setParameter("email", email).getSingleResult();
        return amountOfRow == 0;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = entityManager.merge(toEntity(user));
        return UserRepository.createUser(userEntity);
    }

    @Override
    public Optional<User> findByConfirmationToken(String confirmationToken) {
        String query = "select user from UserEntity user where user.confirmToken=:confirmationToken";
        Optional<UserEntity> result = entityManager.createQuery(query, UserEntity.class).setParameter("confirmationToken", confirmationToken).getResultList().stream().findFirst();

        return result.map(UserRepository::createUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String query = "select user from UserEntity user where user.username=:username";
        return entityManager.createQuery(query, UserEntity.class).setParameter("username", username).getResultList().stream().findFirst().map(UserRepository::createUser);
    }

    @Override
    public User findByIdOrThrowException(long userId) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, userId)).map(UserRepository::createUser).orElseThrow(() -> getElementNotFoundException(userId));
    }


    @Override
    public User remove(User user) {
        long userId = user.getId();
        UserEntity userEntity = Optional.ofNullable(entityManager.find(UserEntity.class, userId)).orElseThrow(() -> getElementNotFoundException(userId));
        entityManager.remove(userEntity);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "select user from UserEntity user where user.email=:email";
        Optional<User> user = entityManager.createQuery(query, UserEntity.class).setParameter("email", email).getResultList().stream().findAny().map(UserRepository::createUser);

        return user;
    }

    @Override
    public Optional<User> findByRestorePasswordToken(String restoreToken) {
        String query = "select user from UserEntity user where user.restorePasswordToken=:restoreToken";
        return entityManager.createQuery(query, UserEntity.class).setParameter("restoreToken", restoreToken).getResultList().stream().findAny().map(UserRepository::createUser);
    }

    @Override
    public List<User> findAllByUserInfoRole(UserRole userRole) {
        return entityManager.createQuery("select user from UserEntity user where user.userRole=:userRole", UserEntity.class).setParameter("userRole", userRole).getResultList().stream().map(UserRepository::createUser).collect(Collectors.toList());
    }

    private ElementNotFoundException getElementNotFoundException(long userId) {
        return new ElementNotFoundException(String.format("Użytkownik z id: %s nie istnieje", userId));
    }

    private UserEntity toEntity(User user) {
        return UserEntityFactory.createUserEntity(user.getSnapshot());
    }
}
