package hr.tvz.application.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import hr.tvz.application.data.User;
import hr.tvz.application.security.AuthenticatedUser;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class UserEndpoint {

    private final AuthenticatedUser authenticatedUser;

    public UserEndpoint(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public Optional<User> getAuthenticatedUser() {
        return authenticatedUser.get();
    }

    @RolesAllowed("ROLE_ADMIN")
    public boolean isAdmin() {
        return true;
    }

    @RolesAllowed("ROLE_USER")
    public boolean isUser() {
        return true;
    }
}
