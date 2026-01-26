package edu.aitu.oop3.db;

import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.entities.Doctor;
import edu.aitu.oop3.db.entities.Patient;
import edu.aitu.oop3.db.exeption.AppointmentException;
import edu.aitu.oop3.db.jdbcrepository.*;
import edu.aitu.oop3.db.repository.*;
import edu.aitu.oop3.db.servise.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static AppointmentRepository repo;
    private static AppointmentService appointmentService;
    private static DoctorAvailabilityService availabilityService;

    public static void main(String[] args) {
        try {
            // Инициализируем все 3 репозитория
            repo = new JdbcAppointmentRepository();
            DoctorRepository docRepo = new JdbcDoctorRepository();
            PatientRepository patRepo = new JdbcPatientRepository();

            availabilityService = new DoctorAvailabilityService(repo);

            // ПЕРЕДАЕМ ВСЕ 4 АРГУМЕНТА (исправляет ошибку в Main)
            appointmentService = new AppointmentService(repo, availabilityService, docRepo, patRepo);

            runMenu();
        } catch (Exception e) {
            System.out.println("Критическая ошибка запуска: " + e.getMessage());
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
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1 -> showDoctorSchedule();
                    case 2 -> bookAppointment();
                    case 3 -> cancelAppointment();
                    case 4 -> addNewDoctor();
                    case 5 -> addNewPatient();
                    case 6 -> { System.out.println("Выход..."); return; }
                    default -> System.out.println("Неверный выбор.");
                }
            } catch (Exception e) {
                System.out.println("\n>>> ОШИБКА: " + e.getMessage());
            }
        }
    }

    // ТВОИ МЕТОДЫ СОХРАНЕНЫ
    private static void showDoctorSchedule() throws SQLException {
        System.out.print("Введите ID врача: ");
        int docId = scanner.nextInt();
        List<Appointment> list = repo.findByDoctorId(docId);
        if (list.isEmpty()) {
            System.out.println("Записей нет.");
        } else {
            for (Appointment app : list) {
                System.out.println("ID: " + app.getId() + " | Время: " + app.getAppointmentTime());
            }
        }
    }

    private static void bookAppointment() throws SQLException, AppointmentException {
        System.out.print("ID врача: "); int dId = scanner.nextInt();
        System.out.print("ID пациента: "); int pId = scanner.nextInt();
        System.out.print("Час (0-23): "); int h = scanner.nextInt();
        System.out.print("Минута (0-59): "); int m = scanner.nextInt();

        LocalDateTime time = LocalDateTime.now().plusDays(1).withHour(h).withMinute(m).withSecond(0).withNano(0);
        appointmentService.bookAppointment(new Appointment(0, dId, pId, time, "SCHEDULED"));
        System.out.println(">>> УСПЕШНО!");
    }

    private static void cancelAppointment() throws SQLException, AppointmentException {
        System.out.print("ID записи: ");
        int id = scanner.nextInt();
        appointmentService.cancelAppointment(id);
    }

    // НОВЫЕ МЕТОДЫ
    private static void addNewDoctor() throws SQLException {
        System.out.print("Имя врача: "); String name = scanner.next();
        System.out.print("Специализация: "); String spec = scanner.next();
        appointmentService.addDoctor(new Doctor(0, name, spec));
        System.out.println("Доктор добавлен!");
    }

    private static void addNewPatient() throws SQLException {
        System.out.print("Имя пациента: "); String name = scanner.next();
        System.out.print("Email: "); String email = scanner.next();
        appointmentService.addPatient(new Patient(0, name, email));
        System.out.println("Пациент добавлен!");
    }
}