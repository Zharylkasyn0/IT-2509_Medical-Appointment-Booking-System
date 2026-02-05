package db.utils;

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

    public Result(boolean isSuccess, T data, String errorMessage) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public T getData() { return data; }
    public String getErrorMessage() { return errorMessage; }
    public boolean isSuccess() { return isSuccess; }
}