package hr.tvz.application.endpoint;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;
import hr.tvz.application.dto.RegisterUserDTO;
import hr.tvz.application.dto.UserDTO;
import hr.tvz.application.services.UserService;

@Endpoint
@AnonymousAllowed
public class RegistrationEndpoint {

    private final UserService userService;

    public RegistrationEndpoint(UserService userService) {
        this.userService = userService;
    }

    public @Nonnull UserDTO registerUser(@Nonnull RegisterUserDTO userDTO) {
        return userService.registerUser(userDTO);
    }
}