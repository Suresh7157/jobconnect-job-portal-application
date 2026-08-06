package com.jobconnect.exception;

/**
 * EmailAlreadyExistsException
 * Thrown when a user attempts registration with an email that is already registered.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
