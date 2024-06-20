package hr.tvz.test.endpoints;

import hr.tvz.application.dto.RegisterUserDTO;
import hr.tvz.application.dto.UserDTO;
import hr.tvz.application.endpoint.RegistrationEndpoint;
import hr.tvz.application.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RegistrationEndpointTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationEndpoint registrationEndpoint;

    @Test
    public void testRegisterUser() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUsername("testuser");
        registerUserDTO.setPassword("password");
        registerUserDTO.setFirstName("Test");
        registerUserDTO.setLastName("User");
        registerUserDTO.setStreet("123 Main St");
        registerUserDTO.setZip(12345);
        registerUserDTO.setCity("TestCity");
        registerUserDTO.setCountry("TestCountry");
        registerUserDTO.setBreedPreferences("TestBreed");
        registerUserDTO.setProfilePicture("profile.jpg".getBytes());
        registerUserDTO.setBudget(1000);
        registerUserDTO.setOpenToAdoptions(true);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        when(userService.registerUser(any(RegisterUserDTO.class))).thenReturn(userDTO);

        UserDTO result = registrationEndpoint.registerUser(registerUserDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userService, times(1)).registerUser(any(RegisterUserDTO.class));
    }
}
