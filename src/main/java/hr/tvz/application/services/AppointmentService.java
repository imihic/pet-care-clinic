package hr.tvz.application.services;

import hr.tvz.application.data.Appointment;
import hr.tvz.application.data.Shelter;
import hr.tvz.application.data.User;
import hr.tvz.application.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAppointmentsByUser(User user) {
        return appointmentRepository.findByUser(user);
    }

    public List<Appointment> getAppointmentsByShelter(Shelter shelter) {
        return appointmentRepository.findByShelter(shelter);
    }

    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
