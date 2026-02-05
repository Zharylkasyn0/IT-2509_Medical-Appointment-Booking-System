package db;

public class MedicalRecord {
    private String diagnosis;
    private String prescription;
    private String notes;

    private MedicalRecord(Builder builder) {
        this.diagnosis = builder.diagnosis;
        this.prescription = builder.prescription;
        this.notes = builder.notes;
    }

    // Вот этого кода не хватало внутри класса Builder:
    public static class Builder {
        private String diagnosis;
        private String prescription;
        private String notes;

        public Builder setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
            return this;
        }

        public Builder setPrescription(String prescription) {
            this.prescription = prescription;
            return this;
        }

        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public MedicalRecord build() {
            return new MedicalRecord(this);
        }
    }

    // Геттеры (если нужны для вывода)
    @Override
    public String toString() {
        return "MedicalRecord{" +
                "diagnosis='" + diagnosis + '\'' +
                ", prescription='" + prescription + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}