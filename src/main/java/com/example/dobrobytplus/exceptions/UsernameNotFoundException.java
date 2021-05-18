package com.example.dobrobytplus.exceptions;

public final class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UsernameNotFoundException(final String message) {
        super(message);
    }

    public UsernameNotFoundException(final Throwable cause) {
        super(cause);
    }

}