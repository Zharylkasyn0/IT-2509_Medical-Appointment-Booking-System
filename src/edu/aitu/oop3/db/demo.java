package edu.aitu.oop3.db;
class SlotAlreadyBookedException extends Exception {
    public SlotAlreadyBookedException(String message) { super(message); }
}

class AppointmentNotFoundException extends Exception {
    public AppointmentNotFoundException(String message) { super(message); }
}