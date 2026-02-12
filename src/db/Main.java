package db;

import db.ClinicConfig;
import db.dto.AppointmentReport;
import db.jdbcrepository.JdbcAppointmentRepository;
import db.jdbcrepository.JdbcDoctorRepository;
import db.jdbcrepository.JdbcPatientRepository;
import db.repositories.AppointmentRepository;
import db.repositories.DoctorRepository;
import db.repositories.PatientRepository;
import db.services.AppointmentService;
import db.services.DoctorAvailabilityService;
import db.entities.Patient;
import db.interfaces.IUser;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import db.entities.Appointment;
import db.entities.Doctor;
import db.exeption.AppointmentException;
import db.jdbcrepository.*;
import db.repositories.*;
import db.services.*;
import db.utils.Result;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static AppointmentRepository repo;
    private static AppointmentService appointmentService;
    private static DoctorAvailabilityService availabilityService;

    public static void main(String[] args) {
        try {

            DatabaseConnection db = DatabaseConnection.getInstance();
            repo = new JdbcAppointmentRepository();
            DoctorRepository docRepo = new JdbcDoctorRepository();
            PatientRepository patRepo = new JdbcPatientRepository();
            availabilityService = new DoctorAvailabilityService(repo);
            appointmentService = new AppointmentService(repo, availabilityService, docRepo, patRepo);


            runDemos();


            runMenu();

        } catch (Exception e) {
            System.out.println("Критическая ошибка запуска: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private static void runDemos() {
        System.out.println("\n--- DEMO BLOCK START ---");


        Patient p1 = new Patient.Builder().setId(1).setName("Алихан").setPhone("777").build();
        Patient p2 = new Patient.Builder().setId(2).setName("Берик").setPhone("888").build();

        List<Patient> patients = new ArrayList<>();
        patients.add(p1);
        patients.add(p2);


        String searchName = "Алихан";
        List<Patient> filtered = patients.stream()
                .filter(p -> p.getName().contains(searchName))
                .collect(Collectors.toList());

        System.out.println("Lambda поиск: " + filtered);


        System.out.println("Factory Demo (Milestone 2):");
        try {
            
            db.entities.Appointment app = db.services.AppointmentFactory.createAppointment("ONLINE")
                    .setId(1)
                    .setDoctorId(10)
                    .setPatientId(100)
                    .setAppointmentTime(java.time.LocalDateTime.now())
                    .setStatus("NEW")
                    .build();
            System.out.println("Created appointment: " + app);
        } catch (Exception e) {
            System.out.println("Error in demo: " + e.getMessage());
        }

        System.out.println("--- DEMO BLOCK END ---\n");
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n--- MEDICAL SYSTEM MENU ---");
            System.out.println("1. Показать расписание врача");
            System.out.println("2. Записаться на прием (Book)");
            System.out.println("3. Отменить запись (Cancel)");
            System.out.println("4. Добавить нового врача");
            System.out.println("5. Зарегистрировать пациента");
            System.out.println("6. Показать записи пациента");
            System.out.println("7. Показать отчет по записям");
            System.out.println("8. Выход");
            System.out.println("Выбери пункт: ");

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
                    case 6 -> showPatientAppointments();
                    case 7 -> showReport();
                    case 8 -> { System.out.println("Выход..."); return; }
                    default -> System.out.println("Неверный выбор.");
                }
            } catch (Exception e) {
                System.out.println("\n>>> ОШИБКА: " + e.getMessage());
            }
        }
    }
    private static void showReport() {
        System.out.println("\n--- ОТЧЁТ ПО ЗАПИСЯМ ---");
        Result<AppointmentReport> result = appointmentService.generateReport();
        if (result.isSuccess()) {
            AppointmentReport report = result.getData();
            System.out.println("Клиника: " + report.getClinicName());
            System.out.println("\nЗаписи по статусам:");
            report.getCountByStatus().forEach((status, count) ->
                    System.out.println("  " + status + ": " + count)
            );
            System.out.println("\nЗаписи по врачам:");
            report.getCountByDoctor().forEach((doctor, count) ->
                    System.out.println("  " + doctor + ": " + count)
            );
        } else {
            System.out.println("Ошибка формирования отчёта: " + result.getErrorMessage());
        }
    }

    private static void showDoctorSchedule() throws SQLException {
        System.out.println("Введите ID врача: ");
        int docId = scanner.nextInt();
        scanner.nextLine();
        Result<List<Appointment>> result = repo.findByDoctorId(docId);
        if (result.isSuccess()) {
            List<Appointment> list = result.getData();
            if (list.isEmpty()) {
                System.out.println("Записей нет.");
            } else {
                for (Appointment app : list) {
                    System.out.println("ID записи: " + app.getId() + " | Статус: " + app.getStatus() + " | Время: " + app.getAppointmentTime());
                }
            }
        }else { System.out.println("Ошибка: " + result.getErrorMessage());

        }
    }

    private static void showPatientAppointments()  {
        System.out.println("Введите ID пациента: ");
        int patientId = scanner.nextInt();
        scanner.nextLine();
        Result<List<Appointment>>result =repo.findByPatientId(patientId);
        if(result.isSuccess()){
        List<Appointment> list = result.getData();
        if (list.isEmpty()) {
            System.out.println("Записей нет.");
        } else {
            for (Appointment app : list) {
                System.out.println("ID записи: " + app.getId() +
                        " | Доктор ID: " + app.getDoctorId() +
                        " | Статус: " + app.getStatus() +
                        " | Время: " + app.getAppointmentTime());
            }
        }
        }else{
                System.out.println("Ошибка: " + result.getErrorMessage());

        }
    }

    private static void bookAppointment() {
        System.out.println("ID врача: ");
        int dId = scanner.nextInt();
        System.out.println("ID пациента: ");
        int pId = scanner.nextInt();
        System.out.println("Час (0-23): ");
        int h = scanner.nextInt();
        System.out.println("Минута (0-59): ");
        int m = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Выберите тип: 1. Онлайн, 2. Лично");
        int typeChoice = scanner.nextInt();
        String type = (typeChoice == 1) ? "ONLINE" : "IN_PERSON";

        LocalDateTime time = LocalDateTime.now().plusDays(1)
                .withHour(h).withMinute(m).withSecond(0).withNano(0);
        ClinicConfig config = ClinicConfig.getInstance();
        if (time.toLocalTime().isBefore(config.getOpeningTime()) ||
                time.toLocalTime().isAfter(config.getClosingTime())) {
            System.out.println("Ошибка: Клиника закрыта в это время.");
            System.out.println("Часы работы: " + config.getOpeningTime() + " - " + config.getClosingTime());
            return;
        }

        Appointment app = AppointmentFactory.createAppointment(type)
                .setId(0)
                .setDoctorId(dId)
                .setPatientId(pId)
                .setAppointmentTime(time)
                .setStatus("SCHEDULED")
                .build();

        Result<Boolean> result = appointmentService.bookAppointment(app);
        if (result.isSuccess()) {
            System.out.println(">>> УСПЕШНО!");
        } else {
            System.out.println("Ошибка: " + result.getErrorMessage());
        }
    }

    private static void cancelAppointment() {
        System.out.print("Введите ID ЗАПИСИ (из списка расписания) для отмены: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Result<Boolean> result = appointmentService.cancelAppointment(id);
        if (result.isSuccess()) {
            System.out.println("Запрос на отмену отправлен.");
        } else {
            System.out.println("Ошибка: " + result.getErrorMessage());
        }
    }

    private static void addNewDoctor() {
        System.out.print("Имя врача: ");
        String name = scanner.nextLine();
        System.out.print("Специализация: ");
        String spec = scanner.nextLine();

        Result<Boolean> result = appointmentService.addDoctor(new Doctor(0, name, spec));
        if (result.isSuccess()) {
            System.out.println("Доктор добавлен!");
        } else {
            System.out.println("Ошибка: " + result.getErrorMessage());
        }
    }

    private static void addNewPatient() {
        System.out.println("Имя пациента: ");
        String name = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Телефон: ");
        String phone = scanner.nextLine();


        Patient patient = new Patient.Builder()
                .setId(0)
                .setName(name)
                .setEmail(email)
                .setPhone(phone)
                .build();

        Result<Boolean> result = appointmentService.addPatient(patient);
        if (result.isSuccess()&& result.getData()) {
            System.out.println("Пациент добавлен!");
        } else {
            System.out.println("Ошибка: " + result.getErrorMessage());
        }
    }
}