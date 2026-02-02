
import edu.aitu.oop3.db.entities.Patient;



import java.util.List;


import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.entities.Doctor;
import edu.aitu.oop3.db.exeption.AppointmentException;
import edu.aitu.oop3.db.jdbcrepository.*;
import edu.aitu.oop3.db.repositories.*;
import edu.aitu.oop3.db.services.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static AppointmentRepository repo;
    private static AppointmentService appointmentService;
    private static DoctorAvailabilityService availabilityService;

     static void main() {
        try {
            repo = new JdbcAppointmentRepository();
            DoctorRepository docRepo = new JdbcDoctorRepository();
            PatientRepository patRepo = new JdbcPatientRepository();

            availabilityService = new DoctorAvailabilityService(repo);
            appointmentService = new AppointmentService(repo, availabilityService, docRepo, patRepo);

            runMenu();
        } catch (Exception e) {
            System.out.println("Критическая ошибка запуска: " + e.getMessage());
            System.err.println("Произошла ошибка: " + e.getMessage());
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
            scanner.nextLine();

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

    private static void showDoctorSchedule() throws SQLException {
        System.out.print("Введите ID врача: ");
        int docId = scanner.nextInt();
        scanner.nextLine();
        List<Appointment> list = repo.findByDoctorId(docId);
        if (list.isEmpty()) {
            System.out.println("Записей нет.");
        } else {
            for (Appointment app : list) {
                System.out.println("ID записи: " + app.getId() + " | Статус: " + app.getStatus() + " | Время: " + app.getAppointmentTime());
            }
        }
    }

    private static void bookAppointment() throws SQLException, AppointmentException {
        System.out.print("ID врача: "); int dId = scanner.nextInt();
        System.out.print("ID пациента: "); int pId = scanner.nextInt();
        System.out.print("Час (0-23): "); int h = scanner.nextInt();
        System.out.print("Минута (0-59): "); int m = scanner.nextInt();
        scanner.nextLine();

        LocalDateTime time = LocalDateTime.now().plusDays(1).withHour(h).withMinute(m).withSecond(0).withNano(0);
        appointmentService.bookAppointment(new Appointment(0, dId, pId, time, "SCHEDULED"));
        System.out.println(">>> УСПЕШНО!");
    }

    private static void cancelAppointment() throws SQLException, AppointmentException {
        System.out.print("Введите ID ЗАПИСИ (из списка расписания) для отмены: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        appointmentService.cancelAppointment(id);
        System.out.println("Запрос на отмену отправлен.");
    }

    private static void addNewDoctor() throws SQLException {
        System.out.print("Имя врача: "); String name = scanner.nextLine();
        System.out.print("Специализация: "); String spec = scanner.nextLine();
        appointmentService.addDoctor(new Doctor(0, name, spec));
        System.out.println("Доктор добавлен!");
    }

    private static void addNewPatient() throws SQLException {
        System.out.print("Имя пациента: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        appointmentService.addPatient(new Patient(0, name, email));
        System.out.println("Пациент добавлен!");
    }

}