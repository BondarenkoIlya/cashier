package com.university.ilya.dao;

/**
 * @author Ilya_Bondarenko
 */
public class DaoException extends Exception {

    public DaoException(String message, Exception e) {
        super(message, e);
    }

    public DaoException(String message) {
        super(message);
    }
}
