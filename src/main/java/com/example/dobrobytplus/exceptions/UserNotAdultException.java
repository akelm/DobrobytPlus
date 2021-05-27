package com.example.dobrobytplus.exceptions;

/**
 * The type User not adult exception.
 */
public class UserNotAdultException extends RuntimeException {
    /**
     * Instantiates a new User not adult exception.
     */
    public UserNotAdultException() {
        super();
    }

    /**
     * Instantiates a new User not adult exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserNotAdultException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new User not adult exception.
     *
     * @param message the message
     */
    public UserNotAdultException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new User not adult exception.
     *
     * @param cause the cause
     */
    public UserNotAdultException(final Throwable cause) {
        super(cause);
    }

}
