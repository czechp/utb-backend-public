package app.web.utb.user.infrastructure.persistence;

import app.web.utb.exception.ElementNotFoundException;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import app.web.utb.user.domain.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
class UserRepositoryImplTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("UserRepository.findByIdOrThrowException - ok")
    @Transactional
    void findByIdOrThrowExceptionTest() {
        //given
        long id = 1L;
        //when
        User user = userRepository.findByIdOrThrowException(1L);
        //then
    }

    @Test
    @DisplayName("UserRepository.findByIdOrThrowException - nok not found")
    @Transactional
    void findByIdOrThrowExceptionNotFoundTest() {
        //given
        long id = Long.MAX_VALUE;
        //when
        //then
        assertThrows(ElementNotFoundException.class, () -> userRepository.findByIdOrThrowException(id));
    }

    @Test
    @DisplayName("UserRepository.remove - ok")
    @Transactional()
    void removeTest() {
        //given
        User userToRemove = userRepository.findByIdOrThrowException(1L);
        //when
        userRepository.remove(userToRemove);
        //then
    }

    @Test
    @DisplayName("UserRepository.remove - nok not found")
    @Transactional()
    void removeNotFoundTest() {
        //given
        User userToRemove = userRepository.findByIdOrThrowException(1L);
        //when
        userRepository.remove(userToRemove);
        //then
    }

    @Transactional
    @DisplayName("UserRepository.findByEmail() - ok")
    @Test
    void findByEmailTest() {
        //given
        String email = "testUser@gmail.com";
        //when
        Optional<User> user = userRepository.findByEmail(email);
        //then
        assertTrue(user.isPresent());
    }

    @Transactional
    @DisplayName("UserRepository.findByRestorePasswordToken() - ok")
    @Test
    void findByRestorePasswordTokenTest() {
        //given
        String restorePasswordToken = "1234-1234-1234";
        //when
        Optional<User> optionalUser = userRepository.findByRestorePasswordToken(restorePasswordToken);
        //then
        assertTrue(optionalUser.isPresent());
        assertEquals(restorePasswordToken, optionalUser.get().getSnapshot().getRestorePasswordToken());
    }

    @Transactional
    @DisplayName("UserRepository.findAllByUserInfoRole() - ok")
    @Test
    @Sql("/testSql/userRepositoryFindAllByUserRoleTest.sql")
    void findAllByUserInfoRoleTest() {
        //given
        UserRole userRole = UserRole.MANAGEMENT;
        //when
        List<User> managements = userRepository.findAllByUserInfoRole(userRole);
        //then
        assertEquals(1, managements.size());
    }

}