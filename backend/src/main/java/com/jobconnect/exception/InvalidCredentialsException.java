package com.jobconnect.exception;

/**
 * InvalidCredentialsException
 * Thrown when user login authentication fails due to incorrect credentials.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
