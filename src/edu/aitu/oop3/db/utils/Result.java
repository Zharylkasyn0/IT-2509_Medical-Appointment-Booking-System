package edu.aitu.oop3.db.utils;

// Milestone 2: Use generics for API responses
public class Result<T> {
    private T data;
    private String errorMessage;
    private boolean isSuccess;

    // Конструктор успеха
    public Result(T data) {
        this.data = data;
        this.isSuccess = true;
    }

    // Конструктор ошибки
    public Result(String errorMessage) {
        this.errorMessage = errorMessage;
        this.isSuccess = false;
    }

    public T getData() { return data; }
    public String getErrorMessage() { return errorMessage; }
    public boolean isSuccess() { return isSuccess; }
}