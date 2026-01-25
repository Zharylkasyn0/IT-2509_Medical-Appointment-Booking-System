package edu.aitu.oop3.db;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Medical System...");

        try {
            // 1. Инициализируем компоненты
            AppointmentRepository repo = new JdbcAppointmentRepository();
            DoctorAvailabilityService availabilityService = new DoctorAvailabilityService(repo);
            AppointmentService appointmentService = new AppointmentService(repo, availabilityService);

            // 2. Проверяем подключение к БД и выводим информацию
            checkDatabaseConnection();

            // 3. Пытаемся забронировать
            System.out.println("\nAttempting to book appointment...");

            // Создаем объект записи
            Appointment myApp = new Appointment(
                    0, // id - будет сгенерирован базой
                    1, // doctor_id (должен существовать в таблице doctors)
                    101, // patient_id (должен существовать в таблице patients)
                    LocalDateTime.now().plusDays(2).withHour(10).withMinute(0).withSecond(0).withNano(0),
                    "SCHEDULED"
            );

            // 4. Пытаемся забронировать через сервис
            appointmentService.bookAppointment(myApp);

            System.out.println("--- SUCCESS! Appointment booked. ---");
            System.out.println("Details:");
            System.out.println("  Doctor ID: " + myApp.getDoctorId());
            System.out.println("  Patient ID: " + myApp.getPatientId());
            System.out.println("  Time: " + myApp.getAppointmentTime());
            System.out.println("  Status: " + myApp.getStatus());

        } catch (AppointmentException e) {
            System.out.println("\n Business error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n System error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void checkDatabaseConnection() {
        try {
            System.out.println("Checking database connection to Supabase...");
            var conn = DatabaseConnection.getConnection();

            // Проверяем существование таблиц
            var metaData = conn.getMetaData();
            var tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            System.out.println("Connected to Supabase successfully!");
            System.out.println("Available tables:");

            int tableCount = 0;
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("  - " + tableName);
                tableCount++;
            }

            if (tableCount == 0) {
                System.out.println("Warning: No tables found in database!");
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            throw new RuntimeException("Cannot connect to database", e);
        }
    }
}