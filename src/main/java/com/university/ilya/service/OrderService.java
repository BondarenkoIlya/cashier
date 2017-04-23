package com.university.ilya.service;

import com.university.ilya.dao.DaoException;
import com.university.ilya.dao.jdbc.DaoFactory;
import com.university.ilya.dao.jdbc.OrderDao;
import com.university.ilya.model.Order;
import org.joda.time.DateTime;

import java.util.List;

public class OrderService {

    public void saveOrder(Order order) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                OrderDao dao = daoFactory.getDao(OrderDao.class);
                order.setTime(DateTime.now());
                dao.create(order);
            }catch (DaoException e){
                throw new ServiceException("Cannot create new order", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
    }


    public List<Order> getAllOrders() throws ServiceException {
        List<Order> orders;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                OrderDao dao = daoFactory.getDao(OrderDao.class);
                orders = dao.getAllOrders();
            }catch (DaoException e){
                throw new ServiceException("Cannot get all orders", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
        return orders;
    }
}
