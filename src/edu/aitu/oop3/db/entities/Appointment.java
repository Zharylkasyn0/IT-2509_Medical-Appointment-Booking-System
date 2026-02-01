package edu.aitu.oop3.db.entities;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private String date;
    private String time;
    private String status;

    // Приватный конструктор, доступный только Билдеру
    private Appointment(AppointmentBuilder builder) {
        this.id = builder.id;
        this.patientId = builder.patientId;
        this.doctorId = builder.doctorId;
        this.date = builder.date;
        this.time = builder.time;
        this.status = builder.status;
    }

    // Геттеры
    public int getId() { return id; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Appointment [Date=" + date + ", Time=" + time + ", Status=" + status + "]";
    }

    // Статический класс Builder
    public static class AppointmentBuilder {
        private int id;
        private int patientId;
        private int doctorId;
        private String date;
        private String time;
        private String status;

        public AppointmentBuilder(int id, int patientId, int doctorId) {
            this.id = id;
            this.patientId = patientId;
            this.doctorId = doctorId;
        }

        public AppointmentBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public AppointmentBuilder setTime(String time) {
            this.time = time;
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