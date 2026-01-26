package edu.aitu.oop3.db;

import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.exeption.AppointmentException;
import edu.aitu.oop3.db.jdbcrepository.JdbcAppointmentRepository;
import edu.aitu.oop3.db.repository.AppointmentRepository;
import edu.aitu.oop3.db.servise.AppointmentService;
import edu.aitu.oop3.db.servise.DoctorAvailabilityService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Сканер для ввода данных
    private static final Scanner scanner = new Scanner(System.in);

    // Сервисы объявляем здесь, чтобы они были доступны во всех методах
    private static AppointmentRepository repo;
    private static AppointmentService appointmentService;
    private static DoctorAvailabilityService availabilityService;

    public static void main(String[] args) {
        try {
            // 1. Инициализация (подключение к базе происходит автоматически при первом запросе)
            repo = new JdbcAppointmentRepository();
            availabilityService = new DoctorAvailabilityService(repo);
            appointmentService = new AppointmentService(repo, availabilityService);

            // 2. Запуск меню
            runMenu();

        } catch (Exception e) {
            System.out.println("Критическая ошибка запуска: " + e.getMessage());
        }
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n--- MEDICAL SYSTEM MENU ---");
            System.out.println("1. Показать расписание врача");
            System.out.println("2. Записаться на прием (Book)");
            System.out.println("3. Отменить запись (Cancel)");
            System.out.println("4. Выход");
            System.out.print("Выбери пункт: ");

            // Проверка на корректный ввод числа
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: Введите число!");
                scanner.next(); // очистка буфера
                continue;
            }

            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        showDoctorSchedule();
                        break;
                    case 2:
                        bookAppointment();
                        break;
                    case 3:
                        cancelAppointment();
                        break;
                    case 4:
                        System.out.println("Выход из программы...");
                        return; // Завершает метод и программу
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (SQLException | AppointmentException e) {
                System.out.println("\n>>> ОШИБКА ОПЕРАЦИИ: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n>>> СИСТЕМНАЯ ОШИБКА: " + e.getMessage());
            }
        }
    }

    // --- МЕТОДЫ МЕНЮ ---

    private static void showDoctorSchedule() throws SQLException {
        System.out.print("Введите ID врача (например, 1): ");
        int docId = scanner.nextInt();

        List<Appointment> list = repo.findByDoctorId(docId);

        if (list.isEmpty()) {
            System.out.println("У этого врача нет записей или врача не существует.");
        } else {
            System.out.println("\n--- РАСПИСАНИЕ ВРАЧА " + docId + " ---");
            for (Appointment app : list) {
                System.out.println("ID записи: " + app.getId() +
                        " | Пациент: " + app.getPatientId() +
                        " | Время: " + app.getAppointmentTime() +
                        " | Статус: " + app.getStatus());
            }
        }
    }

    private static void bookAppointment() throws SQLException, AppointmentException {
        System.out.println("\n--- НОВАЯ ЗАПИСЬ ---");

        System.out.print("ID врача: ");
        int docId = scanner.nextInt();

        System.out.print("ID пациента: ");
        int patId = scanner.nextInt();

        // Упрощенный ввод времени (например, запись на завтра)
        System.out.println("Введите время записи (на завтра):");
        System.out.print("Час (0-23): ");
        int hour = scanner.nextInt();

        System.out.print("Минута (0-59): ");
        int minute = scanner.nextInt();

        // Формируем дату: берем завтрашний день + введенное время
        LocalDateTime appointmentTime = LocalDateTime.now()
                .plusDays(1)
                .withHour(hour)
                .withMinute(minute)
                .withSecond(0)
                .withNano(0);

        // Создаем объект
        Appointment newApp = new Appointment(0, docId, patId, appointmentTime, "SCHEDULED");

        // Пытаемся сохранить
        appointmentService.bookAppointment(newApp);

        System.out.println(">>> УСПЕШНО! Запись создана на " + appointmentTime);
    }

    private static void cancelAppointment() throws SQLException, AppointmentException {
        System.out.print("Введите ID записи для отмены: ");
        int appId = scanner.nextInt();

        appointmentService.cancelAppointment(appId);
        // Сообщение об успехе уже выводится внутри сервиса, но можно добавить и здесь
    }
}