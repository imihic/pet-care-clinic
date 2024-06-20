package hr.tvz.test.endpoints;

import hr.tvz.application.dto.FeaturedPetDTO;
import hr.tvz.application.dto.PetDTO;
import hr.tvz.application.endpoint.PetEndpoint;
import hr.tvz.application.services.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PetEndpointTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetEndpoint petEndpoint;

    @Test
    @WithMockUser(roles = "USER")
    public void testGetFeaturedPets() {
        FeaturedPetDTO featuredPetDTO = new FeaturedPetDTO();
        featuredPetDTO.setName("Buddy");
        when(petService.getFeaturedPets()).thenReturn(Collections.singletonList(featuredPetDTO));

        List<FeaturedPetDTO> result = petEndpoint.getFeaturedPets();

        assertEquals(1, result.size());
        assertEquals("Buddy", result.get(0).getName());
        verify(petService, times(1)).getFeaturedPets();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetFeaturedPetsByShelter() {
        FeaturedPetDTO featuredPetDTO = new FeaturedPetDTO();
        featuredPetDTO.setName("Buddy");
        when(petService.getFeaturedPetsByShelter(1L)).thenReturn(Collections.singletonList(featuredPetDTO));

        List<FeaturedPetDTO> result = petEndpoint.getFeaturedPetsByShelter(1L);

        assertEquals(1, result.size());
        assertEquals("Buddy", result.get(0).getName());
        verify(petService, times(1)).getFeaturedPetsByShelter(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllPets() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Buddy");
        when(petService.getAllPets()).thenReturn(Collections.singletonList(petDTO));

        List<PetDTO> result = petEndpoint.getAllPets();

        assertEquals(1, result.size());
        assertEquals("Buddy", result.get(0).getName());
        verify(petService, times(1)).getAllPets();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetFeaturedPets_Unauthorized() {
        // Attempting to access the getAllPets endpoint with a non-admin user
        assertThrows(AccessDeniedException.class, () -> {
            petEndpoint.getAllPets();
        });
    }
}
