package com.university.ilya.service;

import com.university.ilya.dao.DaoException;
import com.university.ilya.dao.jdbc.DaoFactory;
import com.university.ilya.dao.jdbc.ProductDao;
import com.university.ilya.model.Product;

/**
 * @author Ilya_Bondarenko
 */
public class ProductService {

    public Product insertProduct(Product product) throws ServiceException {
        try(DaoFactory daoFactory = new DaoFactory()) {
           try {
               ProductDao dao = daoFactory.getDao(ProductDao.class);
               product = dao.create(product);
           }catch (DaoException e){
               throw new ServiceException("Cannot create product", e);
           }
        } catch (DaoException e) {
            throw new ServiceException("Cannot create dao factory",e);
        }
        return product;
    }
}
