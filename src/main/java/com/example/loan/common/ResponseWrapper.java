package com.example.loan.common;

/**
 * Simple response wrapper for consistent API or service responses.
 */
public class ResponseWrapper<T> {

    private final boolean success; // Flag for success/failure
    private final String message; // Human-readable message
    private final T data; // Optional payload

    private ResponseWrapper(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /** Success response with data */
    public static <T> ResponseWrapper<T> ok(T data) {
        return new ResponseWrapper<>(true, "OK", data);
    }

    /** Error response with message */
    public static <T> ResponseWrapper<T> fail(String message) {
        return new ResponseWrapper<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
