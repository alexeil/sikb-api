package com.boschat.sikb.persistence.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(Exception e) {
        super(e);
    }
}
