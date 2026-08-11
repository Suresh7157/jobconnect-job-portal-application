package com.jobconnect.exception;

/**
 * ResourceNotFoundException
 * Thrown when a requested resource does not exist in the database.
 * Maps to HTTP 404 Not Found via GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
