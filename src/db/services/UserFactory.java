package db.services; // проверь, чтобы этот путь совпадал с твоей папкой

import db.interfaces.IUser;

// Вспомогательные классы (можно оставить здесь или вынести в отдельные файлы)
class DoctorUser implements IUser {
    @Override
    public void showRole() {
        System.out.println("Система: Вы вошли как Доктор.");
    }
}

class PatientUser implements IUser {
    @Override
    public void showRole() {
        System.out.println("Система: Вы вошли как Пациент.");
    }
}

public class UserFactory {
    // ИСПРАВЛЕНИЕ: Теперь метод принимает только ОДИН аргумент String
    public static IUser createUser(String type) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("DOCTOR")) {
            return new DoctorUser();
        } else if (type.equalsIgnoreCase("PATIENT")) {
            return new PatientUser();
        }
        throw new IllegalArgumentException("Неизвестный тип пользователя: " + type);
    }
}