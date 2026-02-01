package factories;

public class UserFactory {
    public static User createUser(String type) {
        if (type.equalsIgnoreCase("DOCTOR")) return new Doctor();
        if (type.equalsIgnoreCase("ADMIN")) return new Admin();
        return null;
    }
}