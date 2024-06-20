package hr.tvz.application.services;

import hr.tvz.application.data.*;
import hr.tvz.application.dto.ApplicationDTO;
import hr.tvz.application.repository.AdoptionApplicationRepository;
import hr.tvz.application.repository.PetRepository;
import hr.tvz.application.repository.UserRepository;
import hr.tvz.application.util.ApplicationStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {
    private final AdoptionApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public ApplicationService(AdoptionApplicationRepository applicationRepository, UserRepository userRepository, PetRepository petRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    public ApplicationDTO saveApplication(ApplicationDTO applicationDTO) {
        AdoptionApplication application = new AdoptionApplication();
        // Convert DTO to entity
        application.setStatus(ApplicationStatus.valueOf(applicationDTO.getStatus()));
        application.setSubmissionDate(applicationDTO.getSubmissionDate());
        application.setNotes(applicationDTO.getNotes());

        Optional<User> user = userRepository.findById(applicationDTO.getUserId());
        user.ifPresent(application::setUser);

        Optional<Pet> pet = petRepository.findById(applicationDTO.getPetId());
        pet.ifPresent(application::setPet);

        AdoptionApplication savedApplication = applicationRepository.save(application);

        // Convert entity to DTO
        return convertToDTO(savedApplication);
    }

    public List<ApplicationDTO> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ApplicationDTO> getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<ApplicationDTO> getApplicationsByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return Objects.requireNonNull(user.map(value -> applicationRepository.findByUser(value).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList())).orElse(null));
    }

    public List<ApplicationDTO> getApplicationsByStatus(String status) {
        return applicationRepository.findByStatus(ApplicationStatus.valueOf(status)).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    public void rejectApplication(Long id) {
        Optional<AdoptionApplication> application = applicationRepository.findById(id);
        application.ifPresent(value -> {
            value.setStatus(ApplicationStatus.REJECTED);
            applicationRepository.save(value);
        });
    }

    public void approveApplication(Long id) {
        Optional<AdoptionApplication> application = applicationRepository.findById(id);
        application.ifPresent(value -> {
            value.setStatus(ApplicationStatus.APPROVED);
            applicationRepository.save(value);
        });
    }


    private ApplicationDTO convertToDTO(AdoptionApplication application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setUserId(application.getUser().getId());
        applicationDTO.setPetId(application.getPet().getId());
        applicationDTO.setStatus(application.getStatus().name());
        applicationDTO.setSubmissionDate(application.getSubmissionDate());
        applicationDTO.setNotes(application.getNotes());
        return applicationDTO;
    }
}
