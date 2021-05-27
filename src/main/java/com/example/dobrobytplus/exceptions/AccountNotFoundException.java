package com.example.dobrobytplus.exceptions;

/**
 * The type Account not found exception.
 */
public final class AccountNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Account not found exception.
     */
    public AccountNotFoundException() {
        super();
    }

    /**
     * Instantiates a new Account not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public AccountNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Account not found exception.
     *
     * @param message the message
     */
    public AccountNotFoundException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Account not found exception.
     *
     * @param cause the cause
     */
    public AccountNotFoundException(final Throwable cause) {
        super(cause);
    }

}