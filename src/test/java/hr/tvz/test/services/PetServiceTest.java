package hr.tvz.test.services;

import hr.tvz.application.data.Pet;
import hr.tvz.application.data.Shelter;
import hr.tvz.application.dto.FeaturedPetDTO;
import hr.tvz.application.dto.PetDTO;
import hr.tvz.application.repository.PetRepository;
import hr.tvz.application.repository.ShelterRepository;
import hr.tvz.application.services.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private ShelterRepository shelterRepository;

    @InjectMocks
    private PetService petService;

    @Test
    public void testSavePet() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Buddy");
        petDTO.setDescription("Friendly dog");
        petDTO.setAge(2);
        petDTO.setVaccinated(true);
        petDTO.setBirthDate(new Date());
        petDTO.setShelterId(1L);

        Shelter shelter = new Shelter();
        shelter.setId(1L);
        Pet pet = new Pet();
        pet.setShelter(shelter);

        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        PetDTO result = petService.savePet(petDTO);

        assertNotNull(result);
        assertEquals("Buddy", result.getName());
        verify(shelterRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    public void testGetAllPets() {
        Pet pet = new Pet();
        when(petRepository.findAll()).thenReturn(Collections.singletonList(pet));

        List<PetDTO> pets = petService.getAllPets();

        assertEquals(1, pets.size());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    public void testGetPetById() {
        Pet pet = new Pet();
        pet.setId(1L);
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Optional<PetDTO> result = petService.getPetById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeletePet() {
        petService.deletePet(1L);

        verify(petRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetFeaturedPets() {
        Pet pet = new Pet();
        when(petRepository.findByFeaturedTrue()).thenReturn(Collections.singletonList(pet));

        List<FeaturedPetDTO> featuredPets = petService.getFeaturedPets();

        assertEquals(1, featuredPets.size());
        verify(petRepository, times(1)).findByFeaturedTrue();
    }

    @Test
    public void testGetFeaturedPetsByShelter() {
        Pet pet = new Pet();
        when(petRepository.findByShelterIdAndFeaturedTrue(1L)).thenReturn(Collections.singletonList(pet));

        List<FeaturedPetDTO> featuredPets = petService.getFeaturedPetsByShelter(1L);

        assertEquals(1, featuredPets.size());
        verify(petRepository, times(1)).findByShelterIdAndFeaturedTrue(1L);
    }
}
