package com.example.dobrobytplus.exceptions;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException() {
        super();
    }

    public OwnerNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OwnerNotFoundException(final String message) {
        super(message);
    }

    public OwnerNotFoundException(final Throwable cause) {
        super(cause);
    }

}
