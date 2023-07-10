package app.web.utb.configuration.security;

import app.web.utb.user.domain.UserSecuritySnapshot;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

class UserDetailsImpl implements UserDetails {
    private final UserSecuritySnapshot userSecuritySnapshot;

    public UserDetailsImpl(UserSecuritySnapshot userSecuritySnapshot) {
        this.userSecuritySnapshot = userSecuritySnapshot;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userSecuritySnapshot.getRole()));
    }

    @Override
    public String getPassword() {
        return userSecuritySnapshot.getPassword();
    }

    @Override
    public String getUsername() {
        return userSecuritySnapshot.getUsername();
    }

    public String getEmail() {
        return userSecuritySnapshot.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userSecuritySnapshot.isEnabled();
    }

}
