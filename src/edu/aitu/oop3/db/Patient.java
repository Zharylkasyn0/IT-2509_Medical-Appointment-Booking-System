package edu.aitu.oop3.db;
public class Patient {
    private int id;
    private String name;
    private String email;

    public Patient(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}

