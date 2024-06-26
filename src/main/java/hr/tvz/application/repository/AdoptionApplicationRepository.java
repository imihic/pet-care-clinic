package hr.tvz.application.repository;

import hr.tvz.application.data.AdoptionApplication;
import hr.tvz.application.util.ApplicationStatus;
import hr.tvz.application.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long> {
    List<AdoptionApplication> findByUser(User user);
    List<AdoptionApplication> findByStatus(ApplicationStatus status);
}
