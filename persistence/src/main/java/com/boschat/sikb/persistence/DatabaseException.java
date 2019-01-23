package com.boschat.sikb.persistence;

public class DatabaseException extends RuntimeException {

    public DatabaseException(Exception e) {
        super(e);
    }
}
