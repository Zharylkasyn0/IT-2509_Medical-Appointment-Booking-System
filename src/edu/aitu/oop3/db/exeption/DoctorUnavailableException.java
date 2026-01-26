package edu.aitu.oop3.db.exeption;

public class DoctorUnavailableException extends AppointmentException {
    public DoctorUnavailableException() {
        super("The doctor is not available at this time.");
    }
}