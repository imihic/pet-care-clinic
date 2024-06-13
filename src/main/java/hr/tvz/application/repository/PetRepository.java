package hr.tvz.application.repository;

import hr.tvz.application.data.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    //find all pets that are featured
    List<Pet> findByFeaturedTrue();

    // find all pets that are featured and belong to a specific shelter
    List<Pet>  findByShelterIdAndFeaturedTrue(Long shelterId);
}
