package db.entities;

public static class Patient {
    private String name;
    private int age;
    private String phone;
    // ... другие поля

    // Приватный конструктор
    private Patient(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.phone = builder.phone;
    }

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
}

// Использование в Main.java (в методе addNewPatient):
Patient newPatient = new Patient.Builder()
        .setName("Ivan")
        .setAge(30)
        .build();

void main() {
}