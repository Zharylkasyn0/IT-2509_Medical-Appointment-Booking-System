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
