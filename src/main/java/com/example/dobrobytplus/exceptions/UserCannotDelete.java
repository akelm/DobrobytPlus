package com.example.dobrobytplus.exceptions;

/**
 * The type User cannot delete.
 */
public class UserCannotDelete extends RuntimeException {
    /**
     * Instantiates a new User cannot delete.
     */
    public UserCannotDelete() {
        super();
    }

    /**
     * Instantiates a new User cannot delete.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserCannotDelete(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new User cannot delete.
     *
     * @param message the message
     */
    public UserCannotDelete(final String message) {
        super(message);
    }

    /**
     * Instantiates a new User cannot delete.
     *
     * @param cause the cause
     */
    public UserCannotDelete(final Throwable cause) {
        super(cause);
    }

}
