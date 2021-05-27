package com.example.dobrobytplus.exceptions;

/**
 * The type User is already a partner.
 */
public class UserIsAlreadyAPartner extends RuntimeException {
    /**
     * Instantiates a new User is already a partner.
     */
    public UserIsAlreadyAPartner() {
        super();
    }

    /**
     * Instantiates a new User is already a partner.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UserIsAlreadyAPartner(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new User is already a partner.
     *
     * @param message the message
     */
    public UserIsAlreadyAPartner(final String message) {
        super(message);
    }

    /**
     * Instantiates a new User is already a partner.
     *
     * @param cause the cause
     */
    public UserIsAlreadyAPartner(final Throwable cause) {
        super(cause);
    }

}
