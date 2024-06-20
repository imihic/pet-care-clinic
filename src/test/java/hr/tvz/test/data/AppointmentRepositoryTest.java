package hr.tvz.test.data;

import hr.tvz.application.data.Appointment;
import hr.tvz.application.data.User;
import hr.tvz.application.repository.AppointmentRepository;
import hr.tvz.application.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUser() {
        User user = new User();
        user.setUsername("testuser");
        userRepository.save(user);

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointmentRepository.save(appointment);

        List<Appointment> appointments = appointmentRepository.findByUser(user);

        assertEquals(1, appointments.size());
        assertEquals("testuser", appointments.getFirst().getUser().getUsername());
    }

    @Test
    public void testFindByStatus() {
        Appointment appointment = new Appointment();
        appointment.setStatus("PENDING");
        appointmentRepository.save(appointment);

        List<Appointment> appointments = appointmentRepository.findByStatus("PENDING");

        assertEquals(1, appointments.size());
        assertEquals("PENDING", appointments.getFirst().getStatus());
    }
}
