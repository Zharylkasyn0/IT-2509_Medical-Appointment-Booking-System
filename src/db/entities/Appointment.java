package db.entities;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    // ИСПРАВЛЕНИЕ: Используем один LocalDateTime вместо двух String
    private LocalDateTime appointmentTime;
    private String status;

    // Приватный конструктор для Билдера
    public Appointment(AppointmentBuilder builder) {
        this.id = builder.id;
        this.patientId = builder.patientId;
        this.doctorId = builder.doctorId;
        this.appointmentTime = builder.appointmentTime;
        this.status = builder.status;
    }

    // Геттеры
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; } // Возвращает дату и время
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", time=" + appointmentTime +
                ", status='" + status + '\'' +
                '}';
    }

    // Класс Builder (Строитель)
    public static class AppointmentBuilder {
        private int id;
        private int patientId;
        private int doctorId;
        private LocalDateTime appointmentTime;
        private String status;

        public AppointmentBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public AppointmentBuilder setPatientId(int patientId) {
            this.patientId = patientId;
            return this;
        }

        public AppointmentBuilder setDoctorId(int doctorId) {
            this.doctorId = doctorId;
            return this;
        }

        // Этот метод теперь принимает правильный тип LocalDateTime
        public AppointmentBuilder setAppointmentTime(LocalDateTime appointmentTime) {
            this.appointmentTime = appointmentTime;
            return this;
        }

        public AppointmentBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Appointment build() {
            return new Appointment(this);
        }
    }
}