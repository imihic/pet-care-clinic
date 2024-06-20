package hr.tvz.test.services;

import hr.tvz.application.data.Appointment;
import hr.tvz.application.data.User;
import hr.tvz.application.repository.AppointmentRepository;
import hr.tvz.application.services.AppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    public void testSaveAppointment() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = appointmentService.saveAppointment(appointment);

        assertEquals(appointment, result);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    public void testGetAllAppointments() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.findAll()).thenReturn(Collections.singletonList(appointment));

        List<Appointment> appointments = appointmentService.getAllAppointments();

        assertEquals(1, appointments.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetAppointmentById() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Optional<Appointment> result = appointmentService.getAppointmentById(1L);

        assertTrue(result.isPresent());
        assertEquals(appointment, result.get());
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAppointmentsByUser() {
        User user = new User();
        Appointment appointment = new Appointment();
        appointment.setUser(user);

        when(appointmentRepository.findByUser(user)).thenReturn(Collections.singletonList(appointment));

        List<Appointment> appointments = appointmentService.getAppointmentsByUser(user);

        assertEquals(1, appointments.size());
        verify(appointmentRepository, times(1)).findByUser(user);
    }

    @Test
    public void testGetAppointmentsByStatus() {
        Appointment appointment = new Appointment();
        appointment.setStatus("PENDING");

        when(appointmentRepository.findByStatus("PENDING")).thenReturn(Collections.singletonList(appointment));

        List<Appointment> appointments = appointmentService.getAppointmentsByStatus("PENDING");

        assertEquals(1, appointments.size());
        verify(appointmentRepository, times(1)).findByStatus("PENDING");
    }

    @Test
    public void testDeleteAppointment() {
        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}
