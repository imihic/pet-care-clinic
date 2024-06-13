package hr.tvz.application.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
    private UserDetailsService userDetailsService;

    public Optional<UserDetails> getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            String username = jwt.getSubject();
            return Optional.of(userDetailsService.loadUserByUsername(username));
        }
        // Anonymous or no authentication.
        return Optional.empty();
    }

}