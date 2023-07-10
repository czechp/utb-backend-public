package app.web.utb.user.application.service;

import app.web.utb.user.UserFacade;
import app.web.utb.user.UserSnap;
import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import app.web.utb.user.domain.UserRole;
import app.web.utb.user.domain.UserSnapshot;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class UserFacadeImpl implements UserFacade {
    private final UserRepository userRepository;
    @Override
    public Optional<UserSnap> currentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(UserSnap::new);
    }

    @Override
    public List<String> managementEmails() {
        return userRepository.findAllByUserInfoRole(UserRole.MANAGEMENT)
                .stream()
                .map(User::getSnapshot)
                .map(UserSnapshot::getEmail)
                .collect(Collectors.toList());
    }
}
