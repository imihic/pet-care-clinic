package hr.tvz.test.data;

import hr.tvz.application.data.Pet;
import hr.tvz.application.data.Shelter;
import hr.tvz.application.repository.PetRepository;
import hr.tvz.application.repository.ShelterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    @Test
    public void testFindByFeaturedTrue() {
        Pet pet = new Pet();
        pet.setFeatured(true);
        petRepository.save(pet);

        List<Pet> featuredPets = petRepository.findByFeaturedTrue();

        assertEquals(1, featuredPets.size());
        assertEquals(true, featuredPets.get(0).isFeatured());
    }

    @Test
    public void testFindByShelterIdAndFeaturedTrue() {
        Shelter shelter = new Shelter();
        shelter.setName("Test Shelter");
        shelter = shelterRepository.save(shelter);

        Pet pet = new Pet();
        pet.setFeatured(true);
        pet.setShelter(shelter);
        petRepository.save(pet);

        List<Pet> pets = petRepository.findByShelterIdAndFeaturedTrue(shelter.getId());

        assertEquals(1, pets.size());
        assertEquals(true, pets.get(0).isFeatured());
        assertEquals("Test Shelter", pets.get(0).getShelter().getName());
    }
}
