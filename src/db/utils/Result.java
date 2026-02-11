package db.utils;


public class Result<T> {
    private final T data;
    private final String errorMessage;
    private final boolean isSuccess;


    public Result(T data) {
        this.data = data;
        this.errorMessage = null;
        this.isSuccess = true;
    }


    public Result(String errorMessage) {
        this.data = null;
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