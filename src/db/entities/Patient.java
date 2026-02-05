package db.entities;

public class Patient {
    private int id;
    private String name;

    private String email;
    private int phone; // Теперь это int

    private Patient(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;

        this.email = builder.email;
        this.phone = builder.phone;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }

    public String getEmail() { return email; } // Добавили геттер
    public int getPhone() { return phone; }    // Добавили геттер

    @Override
    public String toString() {
        return "Patient{id=" + id + ", name='" + name + "', phone=" + phone + "}";
    }

    // Builder
    public static class Builder {
        private int id;
        private String name;

        private String email;
        private int phone;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }



        public Builder setEmail(String email) {
            this.email = email; // ИСПРАВЛЕНО (было this.name = name)
            return this;
        }

        public Builder setPhone(int phone) {
            this.phone = phone;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }
}