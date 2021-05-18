package com.example.dobrobytplus.exceptions;

public class UserNotAdultException extends RuntimeException {
    public UserNotAdultException() {
        super();
    }

    public UserNotAdultException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotAdultException(final String message) {
        super(message);
    }

    public UserNotAdultException(final Throwable cause) {
        super(cause);
    }

}
