package com.university.ilya.dao.jdbc;

import com.university.ilya.dao.Dao;
import com.university.ilya.dao.DaoException;
import com.university.ilya.model.Order;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * @author Ilya_Bondarenko
 */
public class OrderDao extends DaoEntity implements Dao<Order> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDao.class);

    private static final String PROPERTIES_PATH = "application.properties";
    private static final String CURRENCY = "currency";

    private static final String INSERT_ORDER = "INSERT INTO orders VALUE (NULL ,?,?)";
    private static final String FIND_ORDER_BY_ID = "SELECT id, total_price, time FROM orders WHERE id = ?";
    private static final String UPDATE_ORDER = "UPDATE orders SET total_price = ?, time = ? WHERE id=?";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE id = ?";

    public OrderDao() {
    }

    @Override
    public Order create(Order order) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setOrderInPreparedStatement(order, preparedStatement);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                LOG.debug("Generated id is - {}", id);
                order.setId(id);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error("Get exception while working with order creating om dao layer");
            throw new DaoException("Cannot create statement for creating new order", e);
        }
        return order;
    }

    @Override
    public Order findById(int id) throws DaoException {
        Order order = new Order();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_ORDER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = pickOrderFromResultSet(resultSet);
            }
            resultSet.close();
            if (order.getId() == 0) {
                throw new DaoException("Have no order with id" + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find order by id", e);
        }
        return order;
    }

    @Override
    public void update(Order order) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_ORDER)) {
            setOrderInPreparedStatement(order, preparedStatement);
            preparedStatement.setInt(3,order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.error("Get exception while working with order updating on dao layer");
            throw new DaoException("Cannot create statement for creating new order", e);
        }
    }

    @Override
    public void delete(Order order) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_ORDER)){
            preparedStatement.setInt(1,order.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot get connection for deleting product", e);
        }
    }

    private Order pickOrderFromResultSet(ResultSet resultSet) throws DaoException {
        Order order = new Order();
        Properties properties = new Properties();
        try {
            properties.load(ProductDao.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH));
            order.setId(resultSet.getInt(1));
            order.setTotalPrice(Money.of(CurrencyUnit.of(properties.getProperty(CURRENCY)), resultSet.getDouble(2)));
            order.setTime(new DateTime(resultSet.getTimestamp(3)));
        } catch (SQLException e) {
            throw new DaoException("Cannot pick order from result set", e);
        } catch (IOException e) {
            throw new DaoException("Cannot load property for pick order from result set", e);
        }
        return order;

    }

    private void setOrderInPreparedStatement(Order order, PreparedStatement preparedStatement) throws DaoException {
        try {
            preparedStatement.setDouble(1,order.getTotalPrice().getAmount().doubleValue());
            preparedStatement.setTimestamp(2, new Timestamp(order.getTime().getMillis()));
        } catch (SQLException e) {
            throw new DaoException("Cannot set order in statement",e);
        }
    }
}
