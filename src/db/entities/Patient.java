package db.entities; //  Сверяемся с Main.java

// 1. УБРАЛ "static". Класс должен быть просто public
public class Patient {
    private int id; // Добавил поля, которые используются в Main (строка 69 в PDF)
    private String name;
    private int age;
    private String phone;
    private String email;

    // Приватный конструктор принимает Builder
    private Patient(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
        this.phone = builder.phone;
        this.email = builder.email;
    }

    // Геттеры (нужны для Main и вывода)
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Patient{id=" + id + ", name='" + name + "', phone='" + phone + "'}";
    }

    // 2. Builder должен быть static (это верно)
    public static class Builder {
        private int id;
        private String name;
        private int age;
        private String phone;
        private String email;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Patient build() {
            // Теперь это сработает, так как Patient - обычный класс
            return new Patient(this);
        }
    }
}