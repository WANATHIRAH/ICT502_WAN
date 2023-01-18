package com.example.nrhouserentalsystem;

import javax.servlet.http.Part;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MonthlyPaymentDao {
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
    public void update(String imageFileName,String urlPathforDB,int payId) throws SQLException, FileNotFoundException {

        String status="Pending";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE MONTHLYPAYMENT SET PAYSTATUS=?,PAYRECEIPT=?,PAYDOCPATH=? WHERE PAYID=?");) {
            statement.setString(1, status);
            statement.setString(2, imageFileName);
            statement.setString(3, urlPathforDB);
            statement.setInt(4,payId);

            statement.executeUpdate();
        }
    }

    public void createPay(MonthlyPayment monthlyPayment, int bookingid) throws SQLException, FileNotFoundException {


        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO MONTHLYPAYMENT (PAYPRICE,MONTH,PAYDUEDATE,BOOKINGID) VALUES (?,?,?,?)");) {

            statement.setDouble(1, monthlyPayment.getPayPrice());
            statement.setString(2,monthlyPayment.getMonth());
            statement.setDate(3, monthlyPayment.getPayDueDate());
            statement.setInt(4,bookingid);

            statement.executeUpdate();

        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    public void rentComplete(int bookingid,int hid) throws SQLException, FileNotFoundException {


        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE BOOKINGDETAILS SET RENTALSTATUS='Completed' WHERE bookingid=?");) {
            statement.setInt(1,bookingid);

            statement.executeUpdate();
        }  catch (Exception e) {
        e.printStackTrace();
        }

        try (Connection connection = getConnection();
             PreparedStatement statement2 = connection.prepareStatement("UPDATE HOUSEDETAILS SET HOUSEAVAILABILITY='Available' WHERE houseid=?");) {
            statement2.setInt(1,hid);

            statement2.executeUpdate();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyPay(int payId) throws SQLException, FileNotFoundException {


        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE MONTHLYPAYMENT SET PAYSTATUS='Paid' WHERE payid=?");) {
            statement.setInt(1,payId);

            statement.executeUpdate();
        }  catch (Exception e) {
        e.printStackTrace();
        }
    }

    public void rejectPay(int payId) throws SQLException, FileNotFoundException {


        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE MONTHLYPAYMENT SET PAYSTATUS='Unpaid' WHERE payid=?");) {
            statement.setInt(1,payId);

            statement.executeUpdate();
        }  catch (Exception e) {
         e.printStackTrace();
        }
    }


}
