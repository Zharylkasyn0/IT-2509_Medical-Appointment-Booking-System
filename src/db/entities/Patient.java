package db.entities;

public class Patient {
    private String name;
    private int age;
    private String phone;

    // Приватный конструктор: объект создается только через Builder
    private Patient(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.phone = builder.phone;
    }

    // Статический вложенный класс Builder
    public static class Builder {
        private String name;
        private int age;
        private String phone;

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

        public Patient build() {
            return new Patient(this);
        }
    }

    // Геттеры для полей...
}