package hr.tvz.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = new Pet();
        // Convert DTO to entity
        pet.setName(petDTO.getName());
        pet.setDescription(petDTO.getDescription());
        pet.setAge(petDTO.getAge());
        pet.setVaccinated(petDTO.isVaccinated());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setPhotoUrl(petDTO.getPhotoUrl());

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
        petDTO.setPhotoUrl(pet.getPhotoUrl());
        petDTO.setShelterId(pet.getShelter().getId());
        return petDTO;
    }
}
