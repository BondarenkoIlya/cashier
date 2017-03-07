package com.university.ilya.dao.jdbc.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Class holds singleton instance of Hikari connection pool
 *
 * @author Ilya_Bondarenko
 */

public class ConnectionPoolHolder {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPoolHolder.class);
    private static final String PROPERTY_ADDRESS = "database/database.properties";
    private static final String URL_NAME = "url";
    private static final String USER_NAME = "user";
    private static final String PASSWORD_NAME = "password";
    private static final String DRIVER_NAME = "driver";
    private static final String MAX_POOL_SIZE_NAME = "max.pool.size";
    private static final String AUTO_COMMIT_NAME = "auto.commit";

    private static DataSource pool;

    /**
     * Method initializes or gets already initialized instance of connection pool
     *
     * @return instance of connection pool
     * @throws ConnectionPoolException in case unable to config pool
     */

    public static synchronized DataSource getInstance() throws ConnectionPoolException {
        if (pool == null) {
            HikariConfig config = new HikariConfig();
            try {
                setUpConfig(config);
                pool = new HikariDataSource(config);
                LOG.info("Initialization of Hikari connection pool");
            } catch (IOException e) {
                throw new ConnectionPoolException("Cannot get properties for pool", e);
            }
        }
        return pool;
    }

    private static void setUpConfig(HikariConfig config) throws IOException {
        Properties properties = new Properties();
        properties.load(ConnectionPoolHolder.class.getClassLoader().getResourceAsStream(PROPERTY_ADDRESS));
        config.setJdbcUrl(properties.getProperty(URL_NAME));
        config.setUsername(properties.getProperty(USER_NAME));
        config.setPassword(properties.getProperty(PASSWORD_NAME));
        config.setDriverClassName(properties.getProperty(DRIVER_NAME));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty(MAX_POOL_SIZE_NAME)));
        config.setAutoCommit(Boolean.parseBoolean(properties.getProperty(AUTO_COMMIT_NAME)));
    }


}
