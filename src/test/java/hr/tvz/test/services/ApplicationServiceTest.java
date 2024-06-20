package hr.tvz.test.services;

import hr.tvz.application.data.AdoptionApplication;
import hr.tvz.application.data.Pet;
import hr.tvz.application.data.User;
import hr.tvz.application.dto.ApplicationDTO;
import hr.tvz.application.repository.AdoptionApplicationRepository;
import hr.tvz.application.repository.PetRepository;
import hr.tvz.application.repository.UserRepository;
import hr.tvz.application.services.ApplicationService;
import hr.tvz.application.util.ApplicationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private AdoptionApplicationRepository applicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    public void testSaveApplication() {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setStatus("PENDING");
        applicationDTO.setSubmissionDate(new Date());
        applicationDTO.setNotes("Test notes");
        applicationDTO.setUserId(1L);
        applicationDTO.setPetId(1L);

        User user = new User();
        user.setId(1L);
        Pet pet = new Pet();
        pet.setId(1L);
        AdoptionApplication savedApplication = new AdoptionApplication();
        savedApplication.setId(1L);
        savedApplication.setUser(user);
        savedApplication.setPet(pet);
        savedApplication.setStatus(ApplicationStatus.PENDING);
        applicationDTO.setSubmissionDate(new Date());
        savedApplication.setNotes("Test notes");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(applicationRepository.save(any(AdoptionApplication.class))).thenReturn(savedApplication);

        ApplicationDTO result = applicationService.saveApplication(applicationDTO);

        assertEquals(1L, result.getId());
        assertEquals("PENDING", result.getStatus());
        verify(userRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).findById(1L);
        verify(applicationRepository, times(1)).save(any(AdoptionApplication.class));
    }

    @Test
    public void testGetAllApplications() {
        AdoptionApplication application = new AdoptionApplication();
        User user = new User();
        user.setId(1L);
        Pet pet = new Pet();
        pet.setId(1L);
        application.setUser(user);
        application.setPet(pet);
        application.setStatus(ApplicationStatus.PENDING);
        application.setSubmissionDate(new Date());
        application.setNotes("Test notes");

        when(applicationRepository.findAll()).thenReturn(Collections.singletonList(application));

        List<ApplicationDTO> applications = applicationService.getAllApplications();

        assertEquals(1, applications.size());
        verify(applicationRepository, times(1)).findAll();
    }

    @Test
    public void testGetApplicationById() {
        AdoptionApplication application = new AdoptionApplication();
        User user = new User();
        user.setId(1L);
        Pet pet = new Pet();
        pet.setId(1L);
        application.setId(1L);
        application.setUser(user);
        application.setPet(pet);
        application.setStatus(ApplicationStatus.PENDING);
        application.setSubmissionDate(new Date());
        application.setNotes("Test notes");

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        Optional<ApplicationDTO> result = applicationService.getApplicationById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(applicationRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetApplicationsByUser() {
        User user = new User();
        user.setId(1L);
        AdoptionApplication application = new AdoptionApplication();
        application.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(applicationRepository.findByUser(user)).thenReturn(Collections.singletonList(application));

        List<ApplicationDTO> applications = applicationService.getApplicationsByUser(1L);

        assertEquals(1, applications.size());
        verify(userRepository, times(1)).findById(1L);
        verify(applicationRepository, times(1)).findByUser(user);
    }

    @Test
    public void testGetApplicationsByStatus() {
        AdoptionApplication application = new AdoptionApplication();
        application.setStatus(ApplicationStatus.PENDING);

        when(applicationRepository.findByStatus(ApplicationStatus.PENDING)).thenReturn(Collections.singletonList(application));

        List<ApplicationDTO> applications = applicationService.getApplicationsByStatus("PENDING");

        assertEquals(1, applications.size());
        verify(applicationRepository, times(1)).findByStatus(ApplicationStatus.PENDING);
    }

    @Test
    public void testDeleteApplication() {
        applicationService.deleteApplication(1L);

        verify(applicationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRejectApplication() {
        AdoptionApplication application = new AdoptionApplication();
        application.setId(1L);

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        applicationService.rejectApplication(1L);

        verify(applicationRepository, times(1)).findById(1L);
        verify(applicationRepository, times(1)).save(application);
        assertEquals(ApplicationStatus.REJECTED, application.getStatus());
    }

    @Test
    public void testApproveApplication() {
        AdoptionApplication application = new AdoptionApplication();
        application.setId(1L);

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        applicationService.approveApplication(1L);

        verify(applicationRepository, times(1)).findById(1L);
        verify(applicationRepository, times(1)).save(application);
        assertEquals(ApplicationStatus.APPROVED, application.getStatus());
    }
}
