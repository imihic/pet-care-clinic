package hr.tvz.application.services;

import hr.tvz.application.util.Role;
import hr.tvz.application.data.User;
import hr.tvz.application.repository.UserRepository;

import java.util.EnumSet;
import java.util.Optional;

import hr.tvz.application.dto.RegisterUserDTO;
import hr.tvz.application.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository repository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public UserDTO registerUser(RegisterUserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setHashedPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getFirstName() + " " + userDTO.getLastName());
        user.setStreet(userDTO.getStreet());
        user.setZip(String.valueOf(userDTO.getZip()));
        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());
        user.setBreedPreferences(userDTO.getBreedPreferences());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setBudget(userDTO.getBudget());
        user.setOpenToAdoptions(userDTO.isOpenToAdoptions());
        user.setRoles(EnumSet.of(Role.USER));

        User savedUser = repository.save(user);
        return convertToDTO(savedUser);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setStreet(user.getStreet());
        userDTO.setZip(user.getZip());
        userDTO.setCity(user.getCity());
        userDTO.setCountry(user.getCountry());
        userDTO.setBreedPreferences(user.getBreedPreferences());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setBudget(user.getBudget());
        userDTO.setOpenToAdoptions(user.isOpenToAdoptions());
        return userDTO;
    }

}
