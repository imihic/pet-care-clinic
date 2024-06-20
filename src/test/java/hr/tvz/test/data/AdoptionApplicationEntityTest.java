package hr.tvz.test.data;

import hr.tvz.application.data.AdoptionApplication;
import hr.tvz.application.data.Pet;
import hr.tvz.application.data.User;
import hr.tvz.application.repository.AdoptionApplicationRepository;
import hr.tvz.application.util.ApplicationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AdoptionApplicationEntityTest {

    @Autowired
    private AdoptionApplicationRepository adoptionApplicationRepository;

    @Test
    public void testSaveAndFindAdoptionApplication() {
        AdoptionApplication application = new AdoptionApplication();
        application.setStatus(ApplicationStatus.PENDING);
        application.setSubmissionDate(new Date());
        application.setNotes("Test notes");

        // Assuming User and Pet entities are also defined and have repositories
        User user = new User();
        user.setUsername("testuser");
        // Save the user to get an ID (assuming a UserRepository is available)
        // user = userRepository.save(user);
        application.setUser(user);

        Pet pet = new Pet();
        pet.setName("Test Pet");
        // Save the pet to get an ID (assuming a PetRepository is available)
        // pet = petRepository.save(pet);
        application.setPet(pet);

        AdoptionApplication savedApplication = adoptionApplicationRepository.save(application);

        Optional<AdoptionApplication> foundApplication = adoptionApplicationRepository.findById(savedApplication.getId());
        assertTrue(foundApplication.isPresent());
        assertEquals(ApplicationStatus.PENDING, foundApplication.get().getStatus());
        assertEquals("Test notes", foundApplication.get().getNotes());
    }

    @Test
    public void testUpdateAdoptionApplication() {
        AdoptionApplication application = new AdoptionApplication();
        application.setStatus(ApplicationStatus.PENDING);
        application.setSubmissionDate(new Date());
        application.setNotes("Test notes");

        AdoptionApplication savedApplication = adoptionApplicationRepository.save(application);

        savedApplication.setStatus(ApplicationStatus.APPROVED);
        adoptionApplicationRepository.save(savedApplication);

        Optional<AdoptionApplication> foundApplication = adoptionApplicationRepository.findById(savedApplication.getId());
        assertTrue(foundApplication.isPresent());
        assertEquals(ApplicationStatus.APPROVED, foundApplication.get().getStatus());
    }

    @Test
    public void testDeleteAdoptionApplication() {
        AdoptionApplication application = new AdoptionApplication();
        application.setStatus(ApplicationStatus.PENDING);
        application.setSubmissionDate(new Date());
        application.setNotes("Test notes");

        AdoptionApplication savedApplication = adoptionApplicationRepository.save(application);
        Long applicationId = savedApplication.getId();

        adoptionApplicationRepository.deleteById(applicationId);

        Optional<AdoptionApplication> foundApplication = adoptionApplicationRepository.findById(applicationId);
        assertFalse(foundApplication.isPresent());
    }
}
