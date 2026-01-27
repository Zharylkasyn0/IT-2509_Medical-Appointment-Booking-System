package edu.aitu.oop3.db.entities;

import java.time.LocalDateTime;

public class Appointment {
    private final int id;
    private final int doctorId;
    private final int patientId;
    private final LocalDateTime appointmentTime;
    private final String status;

    public Appointment(int id, int doctorId, int patientId, LocalDateTime appointmentTime, String status) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }


    public int getId() { return id; }
    public int getDoctorId() { return doctorId; }
    public int getPatientId() { return patientId; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public String getStatus() { return status; }
}