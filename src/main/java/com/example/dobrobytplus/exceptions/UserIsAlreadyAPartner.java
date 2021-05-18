package com.example.dobrobytplus.exceptions;

public class UserIsAlreadyAPartner extends RuntimeException {
    public UserIsAlreadyAPartner() {
        super();
    }

    public UserIsAlreadyAPartner(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserIsAlreadyAPartner(final String message) {
        super(message);
    }

    public UserIsAlreadyAPartner(final Throwable cause) {
        super(cause);
    }

}
