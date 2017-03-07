package com.university.ilya.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Ilya_Bondarenko
 */
public class DaoEntity {
    private Connection connection;

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
