package hr.tvz.application.services;

import hr.tvz.application.data.*;
import hr.tvz.application.dto.ApplicationDTO;
import hr.tvz.application.repository.AdoptionApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private AdoptionApplicationRepository applicationRepository;

    public ApplicationDTO saveApplication(ApplicationDTO applicationDTO) {
        AdoptionApplication application = new AdoptionApplication();
        // Convert DTO to entity
        application.setStatus(ApplicationStatus.valueOf(applicationDTO.getStatus()));
        application.setSubmissionDate(applicationDTO.getSubmissionDate());
        application.setNotes(applicationDTO.getNotes());

        Optional<User> user = userRepository.findById(applicationDTO.getUserId());
        if(user.isPresent()) {
            application.setUser(user.get());
        }

        Optional<Pet> pet = petRepository.findById(applicationDTO.getPetId());
        if(pet.isPresent()) {
            application.setPet(pet.get());
        }

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
        return user.map(value -> applicationRepository.findByUser(value).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList())).orElse(null);
    }

    public List<ApplicationDTO> getApplicationsByShelter(Long shelterId) {
        Optional<Shelter> shelter = shelterRepository.findById(shelterId);
        return shelter.map(value -> applicationRepository.findByShelter(value).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList())).orElse(null);
    }

    public List<ApplicationDTO> getApplicationsByStatus(String status) {
        return applicationRepository.findByStatus(ApplicationStatus.valueOf(status)).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
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
