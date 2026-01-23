package edu.aitu.oop3.db;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private int doctorId;
    private int patientId;
    private LocalDateTime appointmentTime;
    private String status;

    // Конструктор с 5 параметрами
    public Appointment(int id, int doctorId, int patientId, LocalDateTime appointmentTime, String status) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Геттеры
    public int getId() { return id; }
    public int getDoctorId() { return doctorId; }
    public int getPatientId() { return patientId; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public String getStatus() { return status; }
}