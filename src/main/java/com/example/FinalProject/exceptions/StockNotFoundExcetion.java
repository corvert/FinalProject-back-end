package com.example.FinalProject.exceptions;

public class StockNotFoundExcetion extends Exception {
    private int errorCode;
    private String message;

    public StockNotFoundExcetion(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "StockNotFoundExcetion{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
