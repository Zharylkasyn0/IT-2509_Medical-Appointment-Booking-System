import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.entities.Doctor;
import edu.aitu.oop3.db.entities.Patient;
import edu.aitu.oop3.db.repositories.*;
import edu.aitu.oop3.db.jdbcrepository.*;
import edu.aitu.oop3.db.services.*;
import edu.aitu.oop3.db.exeption.AppointmentException; // Исправил опечатку в пакете exception

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    // Используем интерфейсы для гибкости (Polymorphism)
    private static AppointmentRepository repo;
    private static DoctorRepository docRepo;
    private static PatientRepository patRepo;

    private static AppointmentService appointmentService;
    private static DoctorAvailabilityService availabilityService;

    public static void main(String[] args) {
        try {
            // Инициализация репозиториев (предполагаем, что они у тебя есть)
            repo = new JdbcAppointmentRepository();
            docRepo = new JdbcDoctorRepository();
            patRepo = new JdbcPatientRepository();

            // Инициализация сервисов
            availabilityService = new DoctorAvailabilityService(repo);
            // Исправил передачу аргументов согласно твоему коду
            appointmentService = new AppointmentService(repo, availabilityService, docRepo, patRepo);

            runMenu();

        } catch (Exception e) {
            System.err.println("Критическая ошибка запуска: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n--- MEDICAL SYSTEM MENU ---");
            System.out.println("1. Показать расписание врача");
            System.out.println("2. Записаться на прием (Book)");
            System.out.println("3. Отменить запись (Cancel)");
            System.out.println("4. Добавить нового врача");
            System.out.println("5. Зарегистрировать пациента");
            System.out.println("6. Выход");
            System.out.print("Выбери пункт: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: Введите число!");
                scanner.next(); // очистка буфера
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Поглощаем символ новой строки после числа

            try {
                switch (choice) {
                    case 1 -> showDoctorSchedule();
                    case 2 -> bookAppointment();
                    case 3 -> cancelAppointment();
                    case 4 -> addNewDoctor();
                    case 5 -> addNewPatient();
                    case 6 -> {
                        System.out.println("Выход...");
                        return;
                    }
                    default -> System.out.println("Неверный выбор.");
                }
            } catch (Exception e) {
                System.out.println("\n>>> ОШИБКА: " + e.getMessage());
            }
        }
    }

    private static void showDoctorSchedule() throws SQLException {
        System.out.print("Введите ID врача: ");
        int docId = scanner.nextInt();
        scanner.nextLine();

        List<Appointment> list = repo.findByDoctorId(docId);

        if (list.isEmpty()) {
            System.out.println("Записей нет.");
        } else {
            // Здесь можно применить Lambda (Requirement Milestone 2) для вывода
            list.forEach(app ->
                    System.out.println("ID записи: " + app.getId() +
                            " | Статус: " + app.getStatus() +
                            " | Время: " + app.getAppointmentTime())
            );
        }
    }

    private static void bookAppointment() throws SQLException, AppointmentException {
        System.out.print("ID врача: ");
        int dId = scanner.nextInt();

        System.out.print("ID пациента: ");
        int pId = scanner.nextInt();

        System.out.print("Час (0-23): ");
        int hour = scanner.nextInt();
        scanner.nextLine(); // очистка

        // Здесь предполагается вызов метода сервиса
        // boolean success = appointmentService.book(dId, pId, hour);
        System.out.println("Функционал записи в процессе доработки...");
    }

    // Заглушки для методов, которые были в switch, но не реализованы в PDF
    private static void cancelAppointment() { System.out.println("Функция отмены..."); }
    private static void addNewDoctor() { System.out.println("Функция добавления врача..."); }
    private static void addNewPatient() { System.out.println("Функция добавления пациента..."); }
}