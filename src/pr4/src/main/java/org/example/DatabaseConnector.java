package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {

    private final Connection connection;

    public DatabaseConnector() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/rksp4", "postgres", "1234");
        init();
    }

    private void init() throws SQLException {
        connection.createStatement().execute("create table if not exists data (id serial primary key, data varchar(255));");
    }

    public void addData(String data) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into data (data) values (?)");
        ps.setString(1, data);
        ps.execute();
    }

    public void deleteData(String data) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from data where data = ?");
        ps.setString(1, data);
        ps.execute();
    }

    public boolean isDataExist(String data) {
        try {
            PreparedStatement ps = connection.prepareStatement("select * from data where data = ?;");
            ps.setString(1, data);
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
