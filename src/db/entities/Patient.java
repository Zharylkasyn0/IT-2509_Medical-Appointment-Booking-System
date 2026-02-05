package db.entities;

import java.util.Collection;

public class Patient {
    private String name;
    private int age;
    private String phone;
    private int id;

    // Приватный конструктор: объект создается только через Builder
    private Patient(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.phone = builder.phone;
        this.id = builder.id;
    }

    public Collection<Object> getName() {
    }


    // Статический вложенный класс Builder
    public static class Builder {
        private String name;
        private int age;
        private String phone;
        private int id;
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

        public Builder setId(int i) {
            this.id = id;
            return this;
        }
    }

    // Геттеры для полей...
    public String getName() { return name; }
}