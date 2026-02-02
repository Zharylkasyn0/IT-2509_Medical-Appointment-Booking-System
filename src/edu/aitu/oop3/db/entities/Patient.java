package edu.aitu.oop3.db.entities;

public class Patient {
    private int id;
    private String name;
    private String email;
    private String phone; // Опционально

    // Приватный конструктор для Билдера
    private Patient(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    // Обычный конструктор (если нужен для JDBC)
    public Patient(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Patient{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }

    // Milestone 2: Builder Pattern
    public static class Builder {
        private int id;
        private String name;
        private String email;
        private String phone;

        public Builder setId(int id) { this.id = id; return this; }
        public Builder setName(String name) { this.name = name; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPhone(String phone) { this.phone = phone; return this; }

        public Patient build() {
            return new Patient(this);
        }
    }
}