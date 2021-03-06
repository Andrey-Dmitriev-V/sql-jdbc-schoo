package ua.com.foxminded.sql.jdbc.school.utils;

import ua.com.foxminded.sql.jdbc.school.dao.Datasource;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtils {

    public static void transaction(Datasource datasource, ConnectionConsumer consumer) throws SQLException {
        try (Connection connection = datasource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                consumer.consume(connection);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new SQLException("Exception in transaction: ", e);
            }
        }
    }

    public interface ConnectionConsumer {
        void consume(Connection connection) throws Exception;
    }
}
