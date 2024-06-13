package hr.tvz.application.endpoint;

import com.vaadin.hilla.Endpoint;
import hr.tvz.application.dto.FeaturedPetDTO;
import hr.tvz.application.services.PetService;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Endpoint
public class PetEndpoint {

    private final PetService petService;

    public PetEndpoint(PetService petService) {
        this.petService = petService;
    }

    @RolesAllowed("ROLE_USER")
    public List<FeaturedPetDTO> getFeaturedPets() {
        return petService.getFeaturedPets();
    }

    @RolesAllowed("ROLE_USER")
    public List<FeaturedPetDTO> getFeaturedPetsByShelter(Long shelterId) {
        return petService.getFeaturedPetsByShelter(shelterId);
    }
}
