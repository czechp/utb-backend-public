package app.web.utb.user.application.handler;

import app.web.utb.user.event.UserRoleAssigned;
import app.web.utb.user.application.command.UserAssignRoleCmd;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import app.web.utb.user.domain.UserRole;
import app.web.utb.user.domain.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class UserAssignRoleCmdHandlerTest {
    @Mock
    UserRepository userRepository;

    UserAssignRoleCmdHandler userAssignRoleCmdHandler;

    @BeforeEach
    void init() {
        this.userAssignRoleCmdHandler = new UserAssignRoleCmdHandler(userRepository);
    }

    @Test
    @DisplayName("UserAssignRoleCmdHandler.assignRole() - ok")
    void assignRoleTest() {
        //given
        final long userId = 1L;
        final UserRole userRole = UserRole.ADMIN;
        final UserAssignRoleCmd userAssignRoleCmd = new UserAssignRoleCmd(userId, userRole);
        final User user = UserTestFactory.getUserForAssignRoleCmdHandler();
        //when
        Mockito.when(userRepository.findByIdOrThrowException(anyLong())).thenReturn(user);
        UserRoleAssigned userRoleAssigned = userAssignRoleCmdHandler.assignRole(userAssignRoleCmd);
        //then
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

}