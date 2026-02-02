package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.entities.Doctor; // Убедитесь, что у вас есть этот класс
import edu.aitu.oop3.db.entities.Patient; // Убедитесь, что у вас есть этот класс
// Если у вас есть общий класс User, импортируйте его, иначе используйте Object

public class UserFactory {

    // Возвращаем Object или общий родительский класс User
    public static Object createUser(String userType, int id, String name, String surname) {
        if (userType == null) {
            return null;
        }

        if (userType.equalsIgnoreCase("DOCTOR")) {
            // Замените на свой конструктор Doctor
            return new Doctor(id, name, surname);
        } else if (userType.equalsIgnoreCase("PATIENT")) {
            // Замените на свой конструктор Patient
            return new Patient(id, name, surname);
        }

        return null;
    }
}