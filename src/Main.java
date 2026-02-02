package edu.aitu.oop3.db;

import edu.aitu.oop3.db.entities.Patient;
import edu.aitu.oop3.db.services.AppointmentFactory;
import edu.aitu.oop3.db.utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // Сюда подключишь свои репозитории
    // private static AppointmentRepository repo = new JdbcAppointmentRepository();

    public static void main(String[] args) {
        // 1. Singleton: Инициализация БД
        DatabaseConnection.getInstance();

        // Запуск меню
        runMenu();
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n--- MEDICAL SYSTEM MENU ---");
            System.out.println("1. Демонстрация Builder (Пациент)");
            System.out.println("2. Демонстрация Factory (Тип приема)");
            System.out.println("3. Демонстрация Generics (Поиск)");
            System.out.println("4. Выход");
            System.out.print("Выбор: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: Введите число!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1 -> demoBuilder();
                case 2 -> demoFactory();
                case 3 -> demoGenerics(); // Лямбды внутри
                case 4 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    // Демонстрация Builder (Milestone 2)
    private static void demoBuilder() {
        System.out.println("\n--- Создание пациентов через Builder ---");
        Patient p1 = new Patient.Builder()
                .setId(1)
                .setName("Алихан")
                .setEmail("ali@mail.com")
                .setPhone("777-111")
                .build();

        System.out.println("Создан: " + p1);
    }

    // Демонстрация Factory (Milestone 2)
    private static void demoFactory() {
        System.out.println("\n--- Создание типа записи через Factory ---");
        System.out.print("Введите тип (ONLINE / IN_PERSON): ");
        String type = scanner.nextLine();

        try {
            var appointment = AppointmentFactory.createAppointment(type);
            System.out.println("Успех: " + appointment.getDescription());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Демонстрация Generics и Lambdas (Milestone 2)
    private static void demoGenerics() {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(1, "Алихан", "a@a.kz"));
        patients.add(new Patient(2, "Берик", "b@b.kz"));
        patients.add(new Patient(3, "Анна", "c@c.kz"));

        System.out.println("\n--- Поиск пациента (Lambda + Generics) ---");
        System.out.print("Введите имя для поиска: ");
        String searchName = scanner.nextLine();

        // Использование Generics (Result)
        Result<List<Patient>> searchResult = searchPatients(patients, searchName);

        if (searchResult.isSuccess()) {
            System.out.println("Найдено пациентов: " + searchResult.getData().size());
            searchResult.getData().forEach(System.out::println);
        } else {
            System.out.println("Ошибка: " + searchResult.getErrorMessage());
        }
    }

    // Метод, возвращающий Generic Result
    private static Result<List<Patient>> searchPatients(List<Patient> list, String name) {
        if (name == null || name.isEmpty()) {
            return new Result<>("Имя поиска не может быть пустым");
        }

        // Milestone 1 & 2: Lambdas
        List<Patient> filtered = list.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return new Result<>(filtered);
    }
}