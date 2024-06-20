package hr.tvz.application.endpoint;

import jakarta.annotation.security.RolesAllowed;

public class RoleCheckEndpoint {

    @RolesAllowed("ROLE_ADMIN")
    public boolean isAdmin() {
        return true;
    }

    @RolesAllowed("ROLE_USER")
    public boolean isUser() {
        return true;
    }
}
