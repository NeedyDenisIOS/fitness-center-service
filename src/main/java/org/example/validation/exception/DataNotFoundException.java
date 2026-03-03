package org.example.validation.exception;

public class DataNotFoundException extends Exception {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String entityType, Object id) {
        super(String.format("%s not found with id %s", entityType, id));
    }
}
