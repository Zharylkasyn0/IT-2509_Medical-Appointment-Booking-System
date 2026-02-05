package db.services;
import db.entities.Appointment;
import java.time.LocalDateTime;

public class AppointmentFactory {
    // Фабричный метод создает заготовку записи с нужным типом
    public static Appointment.AppointmentBuilder createAppointment(String type) {
        if (type.equalsIgnoreCase("ONLINE")) {
            return new Appointment.AppointmentBuilder().setType("Online");
        } else if (type.equalsIgnoreCase("IN_PERSON")) {
            return new Appointment.AppointmentBuilder().setType("In-Person");
        } else {
            throw new IllegalArgumentException("Unknown appointment type");
        }
    }
}