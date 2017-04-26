package com.university.ilya.service;

import com.university.ilya.dao.AbstractDaoFactory;
import com.university.ilya.dao.DaoException;
import com.university.ilya.dao.jdbc.ConsignmentDao;
import com.university.ilya.dao.jdbc.ProductDao;
import com.university.ilya.model.Consignment;
import com.university.ilya.model.Order;
import com.university.ilya.model.Product;

import java.util.List;

public class ConsignmentService {

    public void saveConsignment(Consignment consignment) throws ServiceException {
        try (AbstractDaoFactory daoFactory = AbstractDaoFactory.getDaoFactory()) {
            try {
                ConsignmentDao consignmentDao = daoFactory.getDao(ConsignmentDao.class);
                ProductDao productDao = daoFactory.getDao(ProductDao.class);
                daoFactory.startTransaction();
                Product product = productDao.saveIfNotExist(consignment.getProduct());
                if (product.getId() == 0) {
                    Product byBarcode = productDao.findByBarcode(product.getBarcode());
                    product.setId(byBarcode.getId());
                }
                consignmentDao.create(consignment);
                daoFactory.commitTransaction();
            }catch (DaoException e){
                daoFactory.rollbackTransaction();
                throw new ServiceException("Cannot create new consignment", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
    }


    public List<Consignment> getAllConsignments() throws ServiceException {
        List<Consignment> consignments;
        try (AbstractDaoFactory daoFactory = AbstractDaoFactory.getDaoFactory()) {
            try {
                ConsignmentDao consignmentDao = daoFactory.getDao(ConsignmentDao.class);
                consignments = consignmentDao.getAllConsignments();
            }catch (DaoException e){
                throw new ServiceException("Cannot get all consignments", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
        return consignments;
    }

    public void subtractProducts(Order order) throws ServiceException {
        try (AbstractDaoFactory daoFactory = AbstractDaoFactory.getDaoFactory()) {
            try {
                ConsignmentDao consignmentDao = daoFactory.getDao(ConsignmentDao.class);
                for (Product product : order.getProducts()) {
                    consignmentDao.decreaseActualNumber(product);
                }
            }catch (DaoException e){
                throw new ServiceException("Cannot get all consignments", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
    }
}
