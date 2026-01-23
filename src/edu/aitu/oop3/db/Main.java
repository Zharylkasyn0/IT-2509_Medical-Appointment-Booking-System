package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Medical System...");

        try {
            // 1. Инициализируем компоненты (SOLID)
            AppointmentRepository repo = new JdbcAppointmentRepository();
            DoctorAvailabilityService availabilityService = new DoctorAvailabilityService(repo);
            AppointmentService appointmentService = new AppointmentService(repo, availabilityService);

            // 2. Подготовка данных прямо из кода (чтобы не заходить в Supabase)
            prepareDatabaseData();

            // 3. Создаем объект бронирования
            // Мы используем id доктора = 1 и id пациента = 101
            Appointment myApp = new Appointment(
                    0, // id (база сама назначит)
                    1, // doctor_id
                    101, // patient_id
                    LocalDateTime.now().plusDays(2), // Время: через 2 дня
                    "SCHEDULED"
            );

            // 4. Пытаемся забронировать через сервис
            System.out.println("Attempting to book appointment...");
            appointmentService.bookAppointment(myApp);

            System.out.println("--- SUCCESS! ---");
            System.out.println("Appointment booked for patient 101 with doctor 1.");

        } catch (Exception e) {
            System.out.println("Execution failed:");
            e.printStackTrace();
        }
    }

    // Метод, который записывает доктора и пациента в базу, если их там нет
    private static void prepareDatabaseData() throws java.sql.SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Добавляем доктора (id=1)
            stmt.executeUpdate("INSERT INTO doctors (id, name, specialization) " +
                    "VALUES (1, 'Dr. House', 'Diagnostics') ON CONFLICT (id) DO NOTHING");

            // Добавляем пациента (id=101)
            stmt.executeUpdate("INSERT INTO patients (id, name, email) " +
                    "VALUES (101, 'Adil', 'adil@example.com') ON CONFLICT (id) DO NOTHING");

            System.out.println("Initial data (Doctor/Patient) is ready.");
        }
    }
}