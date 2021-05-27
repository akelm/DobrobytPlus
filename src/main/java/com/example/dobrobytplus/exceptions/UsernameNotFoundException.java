package com.example.dobrobytplus.exceptions;

/**
 * The type Username not found exception.
 */
public final class UsernameNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Username not found exception.
     */
    public UsernameNotFoundException() {
        super();
    }

    /**
     * Instantiates a new Username not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UsernameNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Username not found exception.
     *
     * @param message the message
     */
    public UsernameNotFoundException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Username not found exception.
     *
     * @param cause the cause
     */
    public UsernameNotFoundException(final Throwable cause) {
        super(cause);
    }

}