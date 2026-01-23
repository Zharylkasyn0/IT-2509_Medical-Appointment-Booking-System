package edu.aitu.oop3.db;

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
            // Если время совпадает и запись не отменена — врач занят
            if (app.getAppointmentTime().equals(time) && !"CANCELLED".equals(app.getStatus())) {
                return false;
            }
        }
        return true;
    }
}