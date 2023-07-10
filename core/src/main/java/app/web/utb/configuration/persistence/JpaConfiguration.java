package app.web.utb.configuration.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
class JpaConfiguration {
    @Bean
    public AuditorAware<String> getAuditorAware() {
        return () -> Optional.of(
                Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                        .map(Principal::getName)
                        .orElse("User not identified")
        );
    }
}
