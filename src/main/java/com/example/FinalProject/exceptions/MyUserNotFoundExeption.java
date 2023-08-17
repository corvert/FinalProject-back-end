package com.example.FinalProject.exceptions;

public class MyUserNotFoundExeption extends Exception{

    private int errorCode;
    private String message;

    public MyUserNotFoundExeption(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}

