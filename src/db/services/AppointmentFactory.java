package db.services;

public class AppointmentFactory {
    public static String createAppointment(String type) {
        return switch (type.toUpperCase()) {
            case "ONLINE" -> "Online Appointment Created";
            case "IN_PERSON" -> "In-Person Appointment Created";
            default -> throw new IllegalArgumentException("Unknown type");
        };
    }
}