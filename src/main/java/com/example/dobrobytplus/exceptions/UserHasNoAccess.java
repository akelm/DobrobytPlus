package com.example.dobrobytplus.exceptions;

public class UserHasNoAccess extends RuntimeException {
    public UserHasNoAccess() {
        super();
    }

    public UserHasNoAccess(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserHasNoAccess(final String message) {
        super(message);
    }

    public UserHasNoAccess(final Throwable cause) {
        super(cause);
    }

}
