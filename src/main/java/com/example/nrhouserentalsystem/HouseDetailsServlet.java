package com.example.nrhouserentalsystem;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@MultipartConfig
@WebServlet(name = "HouseDetailsServlet", value = "/HouseDetailsServlet")
public class HouseDetailsServlet extends HttpServlet {


    private HouseDetailsDao hd;
    public void init() {
        hd = new HouseDetailsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createhouse(request, response);
                    break;
//                case "update":
//                    updatehouse(request, response);
//                    break;
//                case "delete":
//                    deletehouse(request, response);
//                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void createhouse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        Integer landlordid = Integer.parseInt(request.getParameter("landlordid"));


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Part f = request.getPart("hPic");
        String FileName=f.getSubmittedFileName();

        try {


            String hName = request.getParameter("hName");
            Double hMP = Double.parseDouble(request.getParameter("Pricepm"));
            String hAddress = request.getParameter("hAddress");
            String hloc = request.getParameter("hloc");
            String hAvailability = request.getParameter("hAvailability");
            int hNoTenants = Integer.parseInt(request.getParameter("NumOfTenant"));
            int hNoRoom = Integer.parseInt(request.getParameter("NumOfRooms"));
            int hNoToilet = Integer.parseInt(request.getParameter("NumOfToilet"));
            int hNoAC = Integer.parseInt(request.getParameter("NumOfAC"));
            String hWifi = request.getParameter("hWifi");
            int hFurniture = Integer.parseInt(request.getParameter("NumOfSofa"));
            int hWM = Integer.parseInt(request.getParameter("NumOfWM"));
            String desc = request.getParameter("Desc");

            HouseDetails house = new HouseDetails();

            house.sethName(hName);
            house.sethMP(hMP);
            house.sethAddress(hAddress);
            house.sethAvailability(hAvailability);
            house.sethNoTenants(hNoTenants);
            house.sethNoRoom(hNoRoom);
            house.sethNoToilet(hNoToilet);
            house.sethNoAC(hNoAC);
            house.sethWifi(hWifi);
            house.sethFurniture(hFurniture);
            house.sethWM(hWM);
            house.setDesc(desc);
            house.setHlocation(hloc);
            house.setLandlordID(landlordid);

            HouseImages houseImages = new HouseImages();
            houseImages.setHousepic(f);
            houseImages.setHousepicname(FileName);


            hd.createhouse(house,houseImages,landlordid);
            response.sendRedirect("landlord-displayHouseList.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}