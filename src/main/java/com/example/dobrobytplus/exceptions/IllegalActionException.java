package com.example.dobrobytplus.exceptions;

/**
 * The type User not adult exception.
 */
public class IllegalActionException extends RuntimeException {
    /**
     * Instantiates a new User not adult exception.
     */
    public IllegalActionException() {
        super();
    }

    /**
     * Instantiates a new User not adult exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public IllegalActionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new User not adult exception.
     *
     * @param message the message
     */
    public IllegalActionException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new User not adult exception.
     *
     * @param cause the cause
     */
    public IllegalActionException(final Throwable cause) {
        super(cause);
    }

}
