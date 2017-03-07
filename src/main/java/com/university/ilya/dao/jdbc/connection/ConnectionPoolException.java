package com.university.ilya.dao.jdbc.connection;

/**
 * @author Ilya_Bondarenko
 */
public class ConnectionPoolException extends Exception {

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }
}
