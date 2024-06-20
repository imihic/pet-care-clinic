package hr.tvz.test.data;

import hr.tvz.application.data.Shelter;
import hr.tvz.application.repository.ShelterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ShelterRepositoryTest {

    @Autowired
    private ShelterRepository shelterRepository;

    @Test
    public void testSaveAndFindById() {
        Shelter shelter = new Shelter();
        shelter.setName("Happy Tails");
        shelter = shelterRepository.save(shelter);

        Optional<Shelter> foundShelter = shelterRepository.findById(shelter.getId());

        assertTrue(foundShelter.isPresent());
        assertEquals("Happy Tails", foundShelter.get().getName());
    }
}
