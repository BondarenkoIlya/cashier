package com.university.ilya.dao.jdbc;

import com.university.ilya.dao.Dao;
import com.university.ilya.dao.DaoException;
import com.university.ilya.model.Product;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Ilya_Bondarenko
 */
public class ProductDao extends DaoEntity implements Dao<Product> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDao.class);

    private static final String PROPERTIES_PATH = "application.properties";
    private static final String CURRENCY = "currency";

    private static final String INSERT_PRODUCT = "INSERT INTO products VALUES (NULL,?,?,?)";
    private static final String FIND_PRODUCT_BY_ID = "SELECT id,barcode,price,name FROM products WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE products SET barcode = ?, price = ? , name = ? WHERE id=?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    private static final String FIND_PRODUCT_BY_BARCODE = "SELECT id,barcode,price,name FROM products WHERE barcode=?";
    private static final String FIND_ALL_PRODUCTS = "SELECT * FROM products";

    public ProductDao() {
    }

    @Override
    public Product create(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setProductInPreparedStatement(product, preparedStatement);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                LOG.debug("Generated id is - {}", id);
                product.setId(id);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error("Get exception while working with product creating om dao layer");
            throw new DaoException("Cannot create statement for creating new product", e);
        }
        return product;
    }

    @Override
    public Product findById(int id) throws DaoException {
        Product product = new Product();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = pickProductFromResultSet(resultSet);
            }
            resultSet.close();
            if (product.getId() == 0) {
                throw new DaoException("Have no product with id" + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find product by id", e);
        }
        return product;
    }

    public Product findByBarcode(int barcode) throws DaoException {
        Product product = new Product();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_PRODUCT_BY_BARCODE)) {
            preparedStatement.setInt(1, barcode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = pickProductFromResultSet(resultSet);
            }
            resultSet.close();
            if (product.getBarcode() == 0) {
                throw new DaoException("Have no product with this barcode ");// TODO important place
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find product by barcode", e);
        }
        return product;
    }

    @Override
    public void update(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_PRODUCT)) {
            setProductInPreparedStatement(product, preparedStatement);
            preparedStatement.setInt(4, product.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.error("Get exception while working with product updating on dao layer");
            throw new DaoException("Cannot create statement for creating new order", e);
        }
    }

    @Override
    public void delete(Product product) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot get connection for deleting product", e);
        }
    }

    public List<Product> findAll() throws DaoException {
        List<Product> products = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_PRODUCTS);
            while (resultSet.next()){
                products.add(pickProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find all products", e);
        }
        return products;
    }

    private Product pickProductFromResultSet(ResultSet resultSet) throws DaoException {
        Product product = new Product();
        try {
            product.setId(resultSet.getInt(1));
            product.setBarcode(resultSet.getInt(2));
            product.setPrice(Money.of(CurrencyUnit.of(getCurrency()), resultSet.getDouble(3)));
            product.setName(resultSet.getString(4));
        } catch (SQLException e) {
            throw new DaoException("Cannot get product from result set", e);
        }
        return product;
    }

    private void setProductInPreparedStatement(Product product, PreparedStatement preparedStatement) throws DaoException {
        try {
            preparedStatement.setInt(1, product.getBarcode());
            preparedStatement.setDouble(2, product.getPrice().getAmount().doubleValue());
            preparedStatement.setString(3, product.getName());
        } catch (SQLException e) {
            throw new DaoException("Cannot set product to prepared statement", e);
        }
    }

    private String getCurrency() throws DaoException {
        try {
            Properties properties = new Properties();
            properties.load(ProductDao.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH));
            return properties.getProperty(CURRENCY);
        } catch (IOException e) {
            throw new DaoException("Cannot load properties for getting product from result set", e);
        }
    }
}
