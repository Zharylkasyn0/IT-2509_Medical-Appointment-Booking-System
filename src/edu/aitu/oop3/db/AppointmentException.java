public class AppointmentException extends Exception {
    public AppointmentException(String message) { super(message); }
}

public class TimeSlotOccupiedException extends AppointmentException {
    public TimeSlotOccupiedException() { super("This time slot is already booked."); }
}

public class DoctorUnavailableException extends AppointmentException {
    public DoctorUnavailableException() { super("The doctor is not available at this time."); }
}