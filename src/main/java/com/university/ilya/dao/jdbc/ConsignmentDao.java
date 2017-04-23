package com.university.ilya.dao.jdbc;

import com.university.ilya.dao.Dao;
import com.university.ilya.dao.DaoException;
import com.university.ilya.model.Consignment;
import com.university.ilya.model.Product;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya_Bondarenko
 */
public class ConsignmentDao extends DaoEntity implements Dao<Consignment> {

    private static final Logger LOG = LoggerFactory.getLogger(ConsignmentDao.class);

    private static final String INSERT_CONSIGNMENT = "INSERT INTO consignments VALUE (NULL ,?,?,?)";
    private static final String FIND_CONSIGNMENT_BY_ID = "SELECT id,number_in_package, actual_number FROM consignments WHERE id = ?";
    private static final String UPDATE_CONSIGNMENT = "UPDATE consignments SET number_in_package = ?, product_id = ?, actual_number = ? WHERE id=?";
    private static final String DELETE_CONSIGNMENT = "DELETE FROM consignments WHERE id = ?";
    private static final String GET_ALL_CONSIGNMENTS_WITH_PRODUCT = "SELECT * FROM consignments JOIN products ON consignments.product_id=products.id ;";
    private static final String DECREASE_ACTUAL_NUMBER = "UPDATE consignments SET actual_number = actual_number - 1 WHERE product_id = ? LIMIT 1";

    public ConsignmentDao() {
    }

    @Override
    public Consignment create(Consignment consignment) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_CONSIGNMENT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setConsignmentInPreparedStatement(consignment, preparedStatement);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                LOG.debug("Generated id is - {}", id);
                consignment.setId(id);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error("Get exception while working with product creating om dao layer");
            throw new DaoException("Cannot create statement for creating new comment", e);
        }
        return consignment;
        }

    @Override
    public Consignment findById(int id) throws DaoException {
        Consignment consignment = new Consignment();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_CONSIGNMENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pickConsignmentFromResultSet(resultSet);
            }
            resultSet.close();
            if (consignment.getId() == 0) {
                throw new DaoException("Have no consignment with id" + id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find product by id", e);
        }
        return consignment;
    }

    @Override
    public void update(Consignment consignment) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_CONSIGNMENT)) {
            setConsignmentInPreparedStatement(consignment, preparedStatement);
            preparedStatement.setInt(3,consignment.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.error("Get exception while working with consignment updating on dao layer");
            throw new DaoException("Cannot create statement for creating new comment", e);
        }
    }

    public List<Consignment> getAllConsignments() throws DaoException {
        List<Consignment> consignments = new ArrayList<>();
        try (Statement statement= getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_CONSIGNMENTS_WITH_PRODUCT);
            while (resultSet.next()) {
                Consignment consignment = pickConsignmentFromResultSet(resultSet);
                consignment.setProduct(getProductFromResultSet(resultSet));
                consignments.add(consignment);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find product by id", e);
        }
        return consignments;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt(4));
        product.setBarcode(resultSet.getInt(6));
        product.setPrice(Money.of(CurrencyUnit.of(Product.currency), resultSet.getDouble(7)));
        product.setName(resultSet.getString(8));
        return product;
    }

    @Override
    public void delete(Consignment consignment) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_CONSIGNMENT)){
            preparedStatement.setInt(1,consignment.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot get connection for deleting consignment", e);
        }
    }

    public void decreaseActualNumber(Product product) throws DaoException {
        try(PreparedStatement statement = getConnection().prepareStatement(DECREASE_ACTUAL_NUMBER)) {
            statement.setInt(1,product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Cannot decrease actual number of products",e);
        }
    }

    private Consignment pickConsignmentFromResultSet(ResultSet resultSet) throws DaoException {
        Consignment consignment = new Consignment();
        try {
            consignment.setId(resultSet.getInt(1));
            consignment.setNumberInPackage(resultSet.getInt(2));
            consignment.setActualNumber(resultSet.getInt(3));
        } catch (SQLException e) {
            throw new DaoException("Cannot pick consignment from result set",e);
        }
        return consignment;
    }

    private void setConsignmentInPreparedStatement(Consignment consignment, PreparedStatement preparedStatement) throws DaoException {
        try {
            preparedStatement.setInt(1, consignment.getNumberInPackage());
            preparedStatement.setInt(2, consignment.getActualNumber());
            preparedStatement.setInt(3,consignment.getProduct().getId());
        } catch (SQLException e) {
            throw new DaoException("Cannot set consignment into prepared statement", e);
        }
    }
}
