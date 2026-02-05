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

    public static class Builder {
        // Реализация методов fluent-сеттеров
    }
}