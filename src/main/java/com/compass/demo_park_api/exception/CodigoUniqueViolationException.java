package com.compass.demo_park_api.exception;

public class CodigoUniqueViolationException extends RuntimeException {

    public CodigoUniqueViolationException(String message) {
        super(message);
    }
}
