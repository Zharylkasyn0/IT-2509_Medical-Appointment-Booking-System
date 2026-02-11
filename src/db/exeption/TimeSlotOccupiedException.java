package db.exeption;

public class TimeSlotOccupiedException extends RuntimeException {
    public TimeSlotOccupiedException(String message) {
        super(message);
    }
}