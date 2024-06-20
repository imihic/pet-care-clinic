package hr.tvz.application.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import hr.tvz.application.data.User;
import hr.tvz.application.repository.UserRepository;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;
    private final UserDetailsServiceImpl userDetailsService;
    private final SecurityUtils securityUtils;

    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository, UserDetailsServiceImpl userDetailsService, SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
        this.userDetailsService = userDetailsService;
        this.securityUtils = securityUtils;
    }

    @Transactional
    public Optional<User> get() {
        Authentication authenticationMethod = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationMethod instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            Optional<UserDetails> userDetails =  securityUtils.getAuthenticatedUser();
            return userDetails.map(details -> userRepository.findByUsername(details.getUsername()));
        } else {
            return Optional.empty();
        }
    }

    public void logout() {
        authenticationContext.logout();
    }

}
