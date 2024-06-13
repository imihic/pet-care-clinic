package hr.tvz.application.services;
import hr.tvz.application.data.Shelter;
import hr.tvz.application.dto.ShelterDTO;
import hr.tvz.application.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShelterService {

    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public ShelterDTO saveShelter(ShelterDTO shelterDTO) {
        Shelter shelter = new Shelter();
        // Convert DTO to entity
        shelter.setName(shelterDTO.getName());
        shelter.setAddress(shelterDTO.getAddress());

        Shelter savedShelter = shelterRepository.save(shelter);

        // Convert entity to DTO
        return convertToDTO(savedShelter);
    }

    public List<ShelterDTO> getAllShelters() {
        return shelterRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ShelterDTO> getShelterById(Long id) {
        return shelterRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void deleteShelter(Long id) {
        shelterRepository.deleteById(id);
    }

    private ShelterDTO convertToDTO(Shelter shelter) {
        ShelterDTO shelterDTO = new ShelterDTO();
        shelterDTO.setId(shelter.getId());
        shelterDTO.setName(shelter.getName());
        shelterDTO.setAddress(shelter.getAddress());
        return shelterDTO;
    }
}
