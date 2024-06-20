package hr.tvz.application.repository;

import hr.tvz.application.data.Appointment;
import hr.tvz.application.data.Shelter;
import hr.tvz.application.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(User user);
    List<Appointment> findByStatus(String status);
}
