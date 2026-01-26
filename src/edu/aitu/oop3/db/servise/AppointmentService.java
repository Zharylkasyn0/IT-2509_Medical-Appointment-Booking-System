package edu.aitu.oop3.db.servise;
import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.exeption.AppointmentException;
import edu.aitu.oop3.db.exeption.TimeSlotOccupiedException;
import edu.aitu.oop3.db.repository.AppointmentRepository;

import java.sql.SQLException;
// Обновите класс AppointmentService
public class AppointmentService {
    private final AppointmentRepository appointmentRepo;
    private final DoctorAvailabilityService availabilityService;

    public AppointmentService(AppointmentRepository appointmentRepo,
                              DoctorAvailabilityService availabilityService) {
        this.appointmentRepo = appointmentRepo;
        this.availabilityService = availabilityService;
    }

    public void bookAppointment(Appointment app) throws SQLException, AppointmentException {
        if (!availabilityService.isAvailable(app.getDoctorId(), app.getAppointmentTime())) {
            throw new TimeSlotOccupiedException(); // [cite: 54]
        }
        appointmentRepo.save(app); // [cite: 55]
    }

    // НОВЫЙ МЕТОД: Отмена записи
    public void cancelAppointment(int appointmentId) throws SQLException, AppointmentException {
        // Логика отмены через репозиторий
        appointmentRepo.updateStatus(appointmentId, "CANCELLED");
        System.out.println("Appointment " + appointmentId + " has been cancelled.");
    }
}