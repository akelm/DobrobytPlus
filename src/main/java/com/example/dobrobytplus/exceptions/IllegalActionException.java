package com.example.dobrobytplus.exceptions;

/**
 * The illegal action excepton
 */
public class IllegalActionException extends RuntimeException {
    /**
     * Instantiates a new illegal action excepton
     */
    public IllegalActionException() {
        super();
    }

    /**
     * Instantiates a new illegal action excepton
     *
     * @param message the message
     * @param cause   the cause
     */
    public IllegalActionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new illegal action excepton
     *
     * @param message the message
     */
    public IllegalActionException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new illegal action excepton
     *
     * @param cause the cause
     */
    public IllegalActionException(final Throwable cause) {
        super(cause);
    }

}
