package edu.aitu.oop3.db.entities;
public class Patient {
    private int id;
    private String name;
    private String email;

    public Patient(int id, String name, String email) {
        this.id = id;
        this.name = name; //smt
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }


        private String phone;
        private String medicalHistory;

        private Patient(Builder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.phone = builder.phone;
            this.medicalHistory = builder.medicalHistory;
        }

        // Builder Class
        public static class Builder {
            private int id;
            private String name;
            private String phone;
            private String medicalHistory;

            public Builder setId(int id) { this.id = id; return this; }
            public Builder setName(String name) { this.name = name; return this; }
            public Builder setPhone(String phone) { this.phone = phone; return this; }
            public Builder setMedicalHistory(String history) { this.medicalHistory = history; return this; }

            public Patient build() {
                return new Patient(this);
            }
        }

        @Override
        public String toString() {
            return "Пациент [ID=" + id + ", Имя=" + name + "]";
        }
    }

