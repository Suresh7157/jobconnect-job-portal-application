package com.jobconnect.exception;

/**
 * RoleNotFoundException
 * Thrown when a requested role is not found in the database.
 */
public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String message) {
        super(message);
    }
}
