package com.university.ilya.service;

import com.university.ilya.dao.DaoException;
import com.university.ilya.dao.jdbc.DaoFactory;
import com.university.ilya.dao.jdbc.OrderDao;
import com.university.ilya.model.Order;
import org.joda.time.DateTime;

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
}
