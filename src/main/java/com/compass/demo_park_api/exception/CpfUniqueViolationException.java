package com.compass.demo_park_api.exception;

public class CpfUniqueViolationException extends RuntimeException {

    public CpfUniqueViolationException (String message) {
        super(message);
    }
}
