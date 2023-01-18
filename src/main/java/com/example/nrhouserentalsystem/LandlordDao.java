package com.example.nrhouserentalsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.System.out;


public class LandlordDao {

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

    public void signup (Landlord landlord) throws SQLException {

        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into LANDLORD(landlordusername,landlordpassword,landlordname,landlordemail,landlordage,landlordphoneno,landlordgender) values(?,?,?,?,?,?,?)");)
        {

            preparedStatement.setString(1, landlord.getLandlordUsername());
            preparedStatement.setString(2, landlord.getLandlordPassword());
            preparedStatement.setString(3, landlord.getLandlordName());
            preparedStatement.setString(4, landlord.getLandlordEmail());
            preparedStatement.setInt(5, landlord.getLandlordAge());
            preparedStatement.setString(6, landlord.getLandlordPhoneNo());
            preparedStatement.setString(7, landlord.getLandlordGender());
            out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean updateUser(Landlord landlord) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE landlord set landlordusername=?,landlordpassword=?,landlordname=?,landlordemail=?,landlordage=?,landlordphoneno=?,landlordgender=? where landlordid=?");)
        {
            preparedStatement.setString(1, landlord.getLandlordUsername());
            preparedStatement.setString(2, landlord.getLandlordPassword());
            preparedStatement.setString(3, landlord.getLandlordName());
            preparedStatement.setString(4, landlord.getLandlordEmail());
            preparedStatement.setInt(5, landlord.getLandlordAge());
            preparedStatement.setString(6, landlord.getLandlordPhoneNo());
            preparedStatement.setString(7, landlord.getLandlordGender());
            preparedStatement.setInt(8, landlord.getLandlordId());


            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from landlord where landlordid=?");) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

}