package edu.aitu.oop3.db;

public class TimeSlotOccupiedException extends AppointmentException {
    public TimeSlotOccupiedException() {
        super("This time slot is already booked.");
    }
}