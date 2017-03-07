package com.university.ilya.dao.jdbc;

import com.university.ilya.dao.Dao;
import com.university.ilya.dao.DaoException;
import com.university.ilya.model.Order;

/**
 * @author Ilya_Bondarenko
 */
public class OrderDao extends DaoEntity implements Dao<Order> {

    public OrderDao() {
    }

    @Override
    public Order create(Order order) throws DaoException {
        return null;
    }

    @Override
    public Order findById(int id) throws DaoException {
        return null;
    }

    @Override
    public void update(Order order) throws DaoException {

    }

    @Override
    public void delete(Order order) throws DaoException {

    }
}
