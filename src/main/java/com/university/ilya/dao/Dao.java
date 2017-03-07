package com.university.ilya.dao;

/**
 * @author Ilya_Bondarenko
 */
public interface Dao<T> {

    T create (T t) throws DaoException;

    T findById (int id) throws DaoException;

    void update (T t) throws DaoException;

    void delete (T t) throws DaoException;
}
