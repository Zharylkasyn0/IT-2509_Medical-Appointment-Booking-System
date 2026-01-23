package edu.aitu.oop3.db;
public class Main {
    public static void main(String[] args) {
        AppointmentRepository repo = new JdbcAppointmentRepository();
        DoctorAvailabilityService availabilityService = new DoctorAvailabilityService(repo);
        AppointmentService appointmentService = new AppointmentService(repo, availabilityService);

        // Данные для новой записи
        Appointment newApp = new Appointment(1, 2, LocalDateTime.of(2024, 12, 20, 10, 0));

        try {
            appointmentService.bookAppointment(newApp);
        } catch (SlotAlreadyBookedException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ошибка БД: " + e.getMessage());
        }
    }
}