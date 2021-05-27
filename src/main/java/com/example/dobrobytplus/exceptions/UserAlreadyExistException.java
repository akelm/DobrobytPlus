package com.example.dobrobytplus.exceptions;

/**
 * The type User already exist exception.
 */
public final class UserAlreadyExistException extends RuntimeException {

    /**
     * Instantiates a new User already exist exception.
     */
    public UserAlreadyExistException() {
        super();
    }

    /**
     * Instantiates a new User already exist exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new User already exist exception.
     *
     * @param message the message
     */
    public UserAlreadyExistException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new User already exist exception.
     *
     * @param cause the cause
     */
    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
    }

}