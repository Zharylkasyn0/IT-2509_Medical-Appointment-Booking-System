package edu.aitu.oop3.db.services;

// Абстрактный тип или интерфейс
interface IAppointmentType {
    String getDescription();
}

class OnlineAppointment implements IAppointmentType {
    public String getDescription() { return "Онлайн консультация (Zoom)"; }
}

class InPersonAppointment implements IAppointmentType {
    public String getDescription() { return "Личный визит в клинику"; }
}

// Milestone 2: Factory Pattern
public class AppointmentFactory {
    public static IAppointmentType createAppointment(String type) {
        return switch (type.toUpperCase()) {
            case "ONLINE" -> new OnlineAppointment();
            case "IN_PERSON" -> new InPersonAppointment();
            default -> throw new IllegalArgumentException("Неизвестный тип записи: " + type);
        };
    }
}