package com.boschat.sikb.persistence;

/**
 * Created by aaubry on 28/04/2017.
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(Exception e) {
        super(e);
    }
}
