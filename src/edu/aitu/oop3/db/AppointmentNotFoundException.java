package edu.aitu.oop3.db;

public class AppointmentNotFoundException extends AppointmentException {
    public AppointmentNotFoundException(int id) {
        super("Appointment with ID " + id + " was not found.");
    }
}