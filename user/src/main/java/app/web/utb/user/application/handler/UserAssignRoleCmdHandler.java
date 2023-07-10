package app.web.utb.user.application.handler;

import app.web.utb.user.event.UserRoleAssigned;
import app.web.utb.user.application.command.UserAssignRoleCmd;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserAssignRoleCmdHandler {
    private final UserRepository userRepository;

    public UserRoleAssigned assignRole(UserAssignRoleCmd userAssignRoleCmd) {
        User user = userRepository.findByIdOrThrowException(userAssignRoleCmd.getUserId());
        UserRoleAssigned userRoleAssigned = user.assignRole(userAssignRoleCmd.getUserRole());
        userRepository.save(user);
        return userRoleAssigned;
    }
}
