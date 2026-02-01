package factories;

interface User {
    void showRole();
}

class Doctor implements User {
    public void showRole() { System.out.println("Роль: Врач"); }
}

class Admin implements User {
    public void showRole() { System.out.println("Роль: Администратор"); }
}

public class UserFactory {
    public static User createUser(String type) {
        if (type.equalsIgnoreCase("DOCTOR")) return new Doctor();
        if (type.equalsIgnoreCase("ADMIN")) return new Admin();
        return null;
    }
}