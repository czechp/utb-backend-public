package app.web.utb.user.application.handler;

import app.web.utb.user.event.UserRemoved;
import app.web.utb.user.application.command.UserRemoveCmd;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserRemoveCmdHandler {
    private final UserRepository userRepository;

    public UserRemoved removeUser(UserRemoveCmd userRemoveCmd) {
        User userToRemove = userRepository.findByIdOrThrowException(userRemoveCmd.getUserId());
        userRepository.remove(userToRemove);
        return userToRemove.userRemovedEvent();
    }
}
