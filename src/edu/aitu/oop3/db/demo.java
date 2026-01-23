package edu.aitu.oop3.db;
public class SlotAlreadyBookedException extends Exception {
    public SlotAlreadyBookedException(String message) { super(message); }
}

public class AppointmentNotFoundException extends Exception {
    public AppointmentNotFoundException(String message) { super(message); }
}