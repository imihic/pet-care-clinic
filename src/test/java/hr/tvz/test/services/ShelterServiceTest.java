package hr.tvz.test.services;

import hr.tvz.application.data.Shelter;
import hr.tvz.application.dto.ShelterDTO;
import hr.tvz.application.repository.ShelterRepository;
import hr.tvz.application.services.ShelterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {

    @Mock
    private ShelterRepository shelterRepository;

    @InjectMocks
    private ShelterService shelterService;

    @Test
    public void testSaveShelter() {
        ShelterDTO shelterDTO = new ShelterDTO();
        shelterDTO.setName("Happy Tails");
        shelterDTO.setAddress("1234 Elm Street");

        Shelter shelter = new Shelter();
        shelter.setId(1L);
        shelter.setName("Happy Tails");
        shelter.setAddress("1234 Elm Street");

        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter);

        ShelterDTO result = shelterService.saveShelter(shelterDTO);

        assertNotNull(result);
        assertEquals("Happy Tails", result.getName());
        assertEquals("1234 Elm Street", result.getAddress());
        verify(shelterRepository, times(1)).save(any(Shelter.class));
    }

    @Test
    public void testGetAllShelters() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        shelter.setName("Happy Tails");
        shelter.setAddress("1234 Elm Street");

        when(shelterRepository.findAll()).thenReturn(Collections.singletonList(shelter));

        List<ShelterDTO> shelters = shelterService.getAllShelters();

        assertEquals(1, shelters.size());
        assertEquals("Happy Tails", shelters.get(0).getName());
        assertEquals("1234 Elm Street", shelters.get(0).getAddress());
        verify(shelterRepository, times(1)).findAll();
    }

    @Test
    public void testGetShelterById() {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        shelter.setName("Happy Tails");
        shelter.setAddress("1234 Elm Street");

        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        Optional<ShelterDTO> result = shelterService.getShelterById(1L);

        assertTrue(result.isPresent());
        assertEquals("Happy Tails", result.get().getName());
        assertEquals("1234 Elm Street", result.get().getAddress());
        verify(shelterRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteShelter() {
        shelterService.deleteShelter(1L);

        verify(shelterRepository, times(1)).deleteById(1L);
    }
}
