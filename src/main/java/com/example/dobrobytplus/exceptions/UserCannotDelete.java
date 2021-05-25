package com.example.dobrobytplus.exceptions;

public class UserCannotDelete extends RuntimeException {
    public UserCannotDelete() {
        super();
    }

    public UserCannotDelete(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserCannotDelete(final String message) {
        super(message);
    }

    public UserCannotDelete(final Throwable cause) {
        super(cause);
    }

}
