package com.university.ilya.dao;

import com.university.ilya.dao.jdbc.DaoEntity;
import com.university.ilya.dao.jdbc.DaoFactory;

/**
 * @author Ilya_Bondarenko
 */
public abstract class AbstractDaoFactory implements AutoCloseable {

    public static AbstractDaoFactory getDaoFactory() throws DaoException {
            return new DaoFactory();
    }

    public abstract <T extends DaoEntity> T getDao(Class<T> clazz) throws DaoException;

    public abstract void startTransaction() throws DaoException;

    public abstract void commitTransaction() throws DaoException;

    public abstract void rollbackTransaction() throws DaoException;


    @Override
    public abstract void close() throws Exception;
}
