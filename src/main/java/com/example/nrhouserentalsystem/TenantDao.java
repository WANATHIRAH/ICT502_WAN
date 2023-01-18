package com.example.nrhouserentalsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.System.out;

public class TenantDao {

    String dbURL = "jdbc:oracle:thin:@localhost:1521:XE"; //ni url dri heroku database
    String user = "NRS";
    String pass = "wana";



    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection= DriverManager.getConnection(dbURL, user, pass);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public void signup (Tenant tenant) throws SQLException {

        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
             ("insert into TENANT(TENANTUSERNAME,TENANTPASSWORD,TENANTNAME,TENANTEMAIL,TENANTAGE,TENANTPHONENO,TENANTGENDER) values(?,?,?,?,?,?,?)");)
        {

            preparedStatement.setString(1, tenant.getTenantUsername());
            preparedStatement.setString(2, tenant.getTenantPassword());
            preparedStatement.setString(3, tenant.getTenantName());
            preparedStatement.setString(4, tenant.getTenantEmail());
            preparedStatement.setInt(5, tenant.getTenantAge());
            preparedStatement.setString(6, tenant.getTenantPhoneNo());
            preparedStatement.setString(7, tenant.getTenantGender());
            out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean updateUser(Tenant tenant) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE tenant set tenantusername=?,tenantpassword=?,tenantname=?,tenantemail=?,tenantage=?,tenantphoneno=?,tenantgender=? where tenantid=?");)
        {
            statement.setString(1, tenant.getTenantUsername());
            statement.setString(2, tenant.getTenantPassword());
            statement.setString(3, tenant.getTenantName());
            statement.setString(4, tenant.getTenantEmail());
            statement.setInt(5, tenant.getTenantAge());
            statement.setString(6, tenant.getTenantPhoneNo());
            statement.setString(7, tenant.getTenantGender());
            statement.setInt(8, tenant.getTenantId());


            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from TENANT where TENANTID=?");) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

}
