package db.exeption;

public class TimeSlotOccupiedException extends AppointmentException {
    public TimeSlotOccupiedException() {
        super("This time slot is already booked.");
    }
}