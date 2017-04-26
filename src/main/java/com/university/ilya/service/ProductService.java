package com.university.ilya.service;

import com.university.ilya.dao.AbstractDaoFactory;
import com.university.ilya.dao.DaoException;
import com.university.ilya.dao.jdbc.ProductDao;
import com.university.ilya.model.Product;

import java.util.List;

/**
 * @author Ilya_Bondarenko
 */
public class ProductService {

    public Product insertProduct(Product product) throws ServiceException {
        try (AbstractDaoFactory daoFactory = AbstractDaoFactory.getDaoFactory()) {
            try {
                ProductDao dao = daoFactory.getDao(ProductDao.class);
                product = dao.create(product);
            } catch (DaoException e) {
                throw new ServiceException("Cannot create product", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
        return product;
    }

    public List<Product> getAllProducts() throws ServiceException {
        List<Product> allProducts;
        try (AbstractDaoFactory daoFactory = AbstractDaoFactory.getDaoFactory()) {
            try {
                ProductDao dao = daoFactory.getDao(ProductDao.class);
                allProducts = dao.findAll();
            }catch (DaoException e){
                throw new ServiceException("Cannot get all products", e);
            }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory", e);
        }
        return allProducts;
    }
}
