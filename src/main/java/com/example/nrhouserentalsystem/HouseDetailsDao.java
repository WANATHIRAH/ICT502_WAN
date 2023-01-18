package com.example.nrhouserentalsystem;

import javax.servlet.http.Part;
import java.io.*;
import java.sql.*;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.lang.System.out;
@MultipartConfig
public class HouseDetailsDao {

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


    public void createhouse(HouseDetails house,String imageFileName,String urlPathforDB) throws SQLException, IOException {

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into housedetails(housename,housemonthlyprice,houseaddress,houselocation,housepublishdate,houseavailability,housenotenants,housenoroom,housenotoilet,housenoac,housewifi,housefurniture,housewm,housedescription,landlordid,houseimagepic,housepath) values(?,?,?,?,localtimestamp,?,?,?,?,?,?,?,?,?,?,?,?)");)
        {
//            FileInputStream fis = new FileInputStream(file);
            ps.setString(1, house.gethName());
            ps.setDouble(2, house.gethMP());
            ps.setString(3, house.gethAddress());
            ps.setString(4, house.getHlocation());
            ps.setString(5, house.gethAvailability());
            ps.setInt(6, house.gethNoTenants());
            ps.setInt(7, house.gethNoRoom());
            ps.setInt(8, house.gethNoToilet());
            ps.setInt(9, house.gethNoAC());
            ps.setString(10, house.gethWifi());
            ps.setInt(11, house.gethFurniture());
            ps.setInt(12, house.gethWM());
            ps.setString(13, house.getDesc());
            ps.setInt(14, house.getLandlordID());
            ps.setString(15,imageFileName);
            ps.setString(16, urlPathforDB);
//            ps.setBinaryStream(15, fis, file.length());
//            ps.setString(16, file.getName());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updatehouse(HouseDetails house,String imageFileName,String urlPathforDB) throws SQLException, IOException {

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE housedetails SET housename=?,housemonthlyprice=?,houseaddress=?,houselocation=?,housepublishdate=localtimestamp,houseavailability=?,housenotenants=?,housenoroom=?,housenotoilet=?,housenoac=?,housewifi=?,housefurniture=?,housewm=?,housedescription=?,houseimagepic=?,housepath=?,landlordid=?  WHERE houseid = ?");)
        {

            ps.setString(1, house.gethName());
            ps.setDouble(2, house.gethMP());
            ps.setString(3, house.gethAddress());
            ps.setString(4, house.getHlocation());
            ps.setString(5, house.gethAvailability());
            ps.setInt(6, house.gethNoTenants());
            ps.setInt(7, house.gethNoRoom());
            ps.setInt(8, house.gethNoToilet());
            ps.setInt(9, house.gethNoAC());
            ps.setString(10, house.gethWifi());
            ps.setInt(11, house.gethFurniture());
            ps.setInt(12, house.gethWM());
            ps.setString(13, house.getDesc());
            ps.setString(14,imageFileName);
            ps.setString(15, urlPathforDB);
            ps.setInt(16, house.getLandlordID());
            ps.setInt(17, house.gethID());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
