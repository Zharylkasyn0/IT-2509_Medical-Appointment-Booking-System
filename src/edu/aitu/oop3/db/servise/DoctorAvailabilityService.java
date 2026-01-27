package edu.aitu.oop3.db.servise;

import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.repository.AppointmentRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DoctorAvailabilityService {
    private final AppointmentRepository repository;

    public DoctorAvailabilityService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public boolean isAvailable(int doctorId, LocalDateTime time) throws SQLException {
        List<Appointment> appointments = repository.findByDoctorId(doctorId);
        for (Appointment app : appointments) {

            if (app.getAppointmentTime().equals(time) && !"CANCELLED".equalsIgnoreCase(app.getStatus())) {
                return false;
            }
        }
        return true;
    }
}