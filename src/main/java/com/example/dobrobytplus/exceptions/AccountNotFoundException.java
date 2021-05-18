package com.example.dobrobytplus.exceptions;

public final class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(final String message) {
        super(message);
    }

    public AccountNotFoundException(final Throwable cause) {
        super(cause);
    }

}