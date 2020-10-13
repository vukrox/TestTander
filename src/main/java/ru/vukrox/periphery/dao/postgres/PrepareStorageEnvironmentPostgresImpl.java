package ru.vukrox.periphery.dao.postgres;

import ru.vukrox.periphery.dao.PrepareStorageEnvironment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareStorageEnvironmentPostgresImpl implements PrepareStorageEnvironment {

    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS testtaskdb (field INTEGER NOT NULL);";

    private Connection connection;

    public PrepareStorageEnvironmentPostgresImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void prepareStorageEnvironment() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_CREATE_TABLE);
            System.out.println("Table \"testtaskdb\" exists or created successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.out.println("PrepareStorageEnvironment error: " + sqlException.getMessage());
        }
    }

}
