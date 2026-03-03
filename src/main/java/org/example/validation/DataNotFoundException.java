package org.example.validation;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String entityType, Object id) {
        super(String.format("%s not found with id %s", entityType, id));
    }
}
