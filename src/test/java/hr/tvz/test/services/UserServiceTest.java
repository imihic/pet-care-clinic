package hr.tvz.test.services;

import hr.tvz.application.data.User;
import hr.tvz.application.dto.RegisterUserDTO;
import hr.tvz.application.dto.UserDTO;
import hr.tvz.application.repository.UserRepository;
import hr.tvz.application.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.get(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.update(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() {
        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testListUsers() {
        Page<User> page = new PageImpl<>(Collections.singletonList(new User()));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<User> result = userService.list(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void testListUsersWithFilter() {
        Page<User> page = new PageImpl<>(Collections.singletonList(new User()));
        Specification<User> spec = mock(Specification.class);
        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<User> result = userService.list(Pageable.unpaged(), spec);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    public void testCountUsers() {
        when(userRepository.count()).thenReturn(5L);

        int result = userService.count();

        assertEquals(5, result);
        verify(userRepository, times(1)).count();
    }

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

        User user = new User();
        user.setUsername("testuser");
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.registerUser(registerUserDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }
}
