package com.example.dobrobytplus.exceptions;

/**
 * The type User has no access.
 */
public class UserHasNoAccess extends RuntimeException {
    /**
     * Instantiates a new User has no access.
     */
    public UserHasNoAccess() {
        super();
    }

    /**
     * Instantiates a new User has no access.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserHasNoAccess(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new User has no access.
     *
     * @param message the message
     */
    public UserHasNoAccess(final String message) {
        super(message);
    }

    /**
     * Instantiates a new User has no access.
     *
     * @param cause the cause
     */
    public UserHasNoAccess(final Throwable cause) {
        super(cause);
    }

}
