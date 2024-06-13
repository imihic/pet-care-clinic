package hr.tvz.application.services;

import hr.tvz.application.data.Pet;
import hr.tvz.application.data.Shelter;
import hr.tvz.application.dto.FeaturedPetDTO;
import hr.tvz.application.dto.PetDTO;
import hr.tvz.application.repository.PetRepository;
import hr.tvz.application.repository.ShelterRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final ShelterRepository shelterRepository;

    public PetService(PetRepository petRepository, ShelterRepository shelterRepository) {
        this.petRepository = petRepository;
        this.shelterRepository = shelterRepository;
    }

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = new Pet();
        // Convert DTO to entity
        pet.setName(petDTO.getName());
        pet.setDescription(petDTO.getDescription());
        pet.setAge(petDTO.getAge());
        pet.setVaccinated(petDTO.isVaccinated());
        pet.setBirthDate(petDTO.getBirthDate());

        Optional<Shelter> shelter = shelterRepository.findById(petDTO.getShelterId());
        if(shelter.isPresent()) {
            pet.setShelter(shelter.get());
        }

        Pet savedPet = petRepository.save(pet);

        // Convert entity to DTO
        return convertToDTO(savedPet);
    }

    public List<PetDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PetDTO> getPetById(Long id) {
        return petRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    private PetDTO convertToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setDescription(pet.getDescription());
        petDTO.setAge(pet.getAge());
        petDTO.setVaccinated(pet.isVaccinated());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setShelterId(pet.getShelter().getId());
        return petDTO;
    }

    public List<FeaturedPetDTO> getFeaturedPets() {
        return petRepository.findByFeaturedTrue().stream()
                .map(this::convertToFeaturedDTO)
                .collect(Collectors.toList());
    }

    private FeaturedPetDTO convertToFeaturedDTO(Pet pet) {
        FeaturedPetDTO featuredPetDTO = new FeaturedPetDTO();
        featuredPetDTO.setName(pet.getName());
        featuredPetDTO.setDescription(pet.getDescription());
        featuredPetDTO.setPhoto(pet.getPhoto());
        return featuredPetDTO;
    }

    public  List<FeaturedPetDTO> getFeaturedPetsByShelter(Long shelterId) {
        return petRepository.findByShelterIdAndFeaturedTrue(shelterId).stream()
                .map(this::convertToFeaturedDTO)
                .collect(Collectors.toList());
    }
}
