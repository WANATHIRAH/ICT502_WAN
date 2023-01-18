package com.example.nrhouserentalsystem;



import javax.servlet.http.Part;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.System.out;

public class BookingDao {

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

    public void create(Tenant tenant, House house, Landlord landlord) throws Exception{
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into BOOKINGDETAILS(TENANTID,HOUSEID,landlordid) values(?,?,?)");)
        {

            preparedStatement.setInt(1, tenant.getTenantId());
            preparedStatement.setInt(2, house.getHouseid());
            preparedStatement.setInt(3, landlord.getLandlordId());

            out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            printSQLException(e);
        }
    }
    public void depo(String imageFileName,String urlPathforDB,int bookingid) throws SQLException, FileNotFoundException {


            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement("UPDATE BOOKINGDETAILS SET BOOKINGDEPO=?,DEPODOCPATH=? WHERE BOOKINGID=?");) {

                statement.setString(1, imageFileName);
                statement.setString(2, urlPathforDB);
                statement.setInt(3, bookingid);

                statement.executeUpdate();
            }catch (SQLException e) {
                printSQLException(e);
            }
        }
    public void agree(String imageFileName,String urlPathforDB,int bookingid) throws SQLException, FileNotFoundException {

        String status = "In Process";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE BOOKINGDETAILS SET bookingstatus=?,BOOKINGAGREEMENT=?,AGREEDOCPATH=? WHERE BOOKINGID=?");) {
            statement.setString(1, status);
            statement.setString(2, imageFileName);
            statement.setString(3, urlPathforDB);
            statement.setInt(4, bookingid);

            statement.executeUpdate();
        }catch (SQLException e) {
            printSQLException(e);
        }
    }

    public boolean cancelbooking(int bookingid,int houseid) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("update BOOKINGDETAILS set bookingstatus = 'Canceled', rentalstatus='' where BOOKINGID=?");) {
            statement.setInt(1, bookingid);
            rowDeleted = statement.executeUpdate() > 0;

            PreparedStatement statement3 = connection.prepareStatement("UPDATE HOUSEDETAILS SET houseavailability='Available' WHERE houseid=?");
            statement3.setInt(1, houseid);
            statement3.executeUpdate();
        }
        return rowDeleted;
    }

    public void approvedbooking(int bookingid,String imageFileName, String urlPathforDB,int houseid) throws SQLException, FileNotFoundException {
        String status="Approved";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE BOOKINGDETAILS SET BOOKINGSTATUS=?,BOOKINGAGREEMENT=?, AGREEDOCPATH=?, BOOKINGAPPROVALDATE=localtimestamp WHERE BOOKINGID=?");) {

            statement.setString(1, status);
            statement.setString(2, imageFileName);
            statement.setString(3, urlPathforDB);
            statement.setInt(4, bookingid);
            int row = statement.executeUpdate();

            if (row == 1){
                PreparedStatement statement2 = connection.prepareStatement("UPDATE BOOKINGDETAILS SET BOOKINGSTATUS='Canceled' WHERE BOOKINGSTATUS='Pending' AND houseid=?");
                statement2.setInt(1, houseid);
                statement2.executeUpdate();
                System.out.println("All booking that pending already canceled after one approved");

                PreparedStatement statement3 = connection.prepareStatement("UPDATE HOUSEDETAILS SET houseavailability='Not Available' WHERE houseid=?");
                statement3.setInt(1, houseid);
                statement3.executeUpdate();
                System.out.println("House already unavailable");
            }
        }

    }

    public void verifybook(int bookingid) throws SQLException, FileNotFoundException {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE BOOKINGDETAILS SET BOOKINGSTATUS='Completed',RENTALSTATUS='On Going' WHERE BOOKINGID=?");) {
            statement.setInt(1, bookingid);
            statement.executeUpdate();
            System.out.println("booking verify success!");
        }
    }

    public void rejectbook(int bookingid) throws SQLException, FileNotFoundException {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE BOOKINGDETAILS SET BOOKINGSTATUS='Approved' WHERE BOOKINGID=?");) {
            statement.setInt(1, bookingid);
            statement.executeUpdate();
        }
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
