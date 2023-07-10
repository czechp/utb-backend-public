package app.web.utb.configuration.security;

import app.web.utb.user.domain.User;
import app.web.utb.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(User::userSecuritySnapshot)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Account with username: {} does not exist", username)));
    }
}
