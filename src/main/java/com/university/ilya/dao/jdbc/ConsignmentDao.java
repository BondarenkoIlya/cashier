package com.university.ilya.dao.jdbc;

import com.university.ilya.dao.Dao;
import com.university.ilya.dao.DaoException;
import com.university.ilya.model.Consignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ilya_Bondarenko
 */
public class ConsignmentDao extends DaoEntity implements Dao<Consignment> {

    private static final Logger LOG = LoggerFactory.getLogger(ConsignmentDao.class);

    private static final String INSERT_CONSIGNMENT = "INSERT INTO consignments VALUE (NULL ,?,?)";
    private static final String FIND_CONSIGNMENT_BY_ID = "SELECT id,number FROM consignments WHERE id = ?";
    private static final String UPDATE_CONSIGNMENT = "UPDATE consignments SET number = ?, product_id = ? WHERE id=?";
    private static final String DELETE_CONSIGNMENT = "DELETE FROM consignments WHERE id = ?";

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
                consignment.setId(resultSet.getInt(1));
                consignment.setNumber(resultSet.getInt(2));
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

    @Override
    public void delete(Consignment consignment) throws DaoException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_CONSIGNMENT)){
            preparedStatement.setInt(1,consignment.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot get connection for deleting consignment", e);
        }
    }

    private void setConsignmentInPreparedStatement(Consignment consignment, PreparedStatement preparedStatement) throws DaoException {
        try {
            preparedStatement.setInt(1, consignment.getNumber());
            preparedStatement.setInt(2,consignment.getProduct().getId());
        } catch (SQLException e) {
            throw new DaoException("Cannot set consignment into prepared statement", e);
        }
    }
}
