package com.example.dobrobytplus.exceptions;

/**
 * The type Owner not found exception.
 */
public class OwnerNotFoundException extends RuntimeException {
    /**
     * Instantiates a new Owner not found exception.
     */
    public OwnerNotFoundException() {
        super();
    }

    /**
     * Instantiates a new Owner not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public OwnerNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Owner not found exception.
     *
     * @param message the message
     */
    public OwnerNotFoundException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Owner not found exception.
     *
     * @param cause the cause
     */
    public OwnerNotFoundException(final Throwable cause) {
        super(cause);
    }

}
