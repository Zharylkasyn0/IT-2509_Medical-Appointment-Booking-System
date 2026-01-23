package edu.aitu.oop3.db;

import java.sql.SQLException;

public class AppointmentService {
    private final AppointmentRepository appointmentRepo;
    private final DoctorAvailabilityService availabilityService;

    public AppointmentService(AppointmentRepository appointmentRepo, DoctorAvailabilityService availabilityService) {
        this.appointmentRepo = appointmentRepo;
        this.availabilityService = availabilityService;
    }

    public void bookAppointment(Appointment app) throws SQLException, Exception {
        if (!availabilityService.isAvailable(app.getDoctorId(), app.getAppointmentTime())) {
            // Здесь используй свои кастомные исключения из AppointmentException.java
            throw new Exception("Time slot is already occupied!");
        }
        appointmentRepo.save(app);
    }
}