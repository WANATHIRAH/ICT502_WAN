<%--
  Created by IntelliJ IDEA.
  User: TOSHIBA
  Date: 3/1/2022
  Time: 1:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>HOUSE RENTAL SYSTEM</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="landlord-login.css">
    <link href="https://emoji-css.afeld.me/emoji.css" rel="stylesheet">
</head>

<body >
<div class="header">
    <p>UiTM JASIN NON-RESIDENT HOUSE RENTAL SYSTEM</p>
</div>
<form action="/LandlordLoginServlet" method="POST">
    <div class="container">
        <h2>LANDLORD LOGIN</h2>
        <div class="row">
            <div class="col-25">
                <label style=""></label>
            </div>
            <div class="col-75">
                <input type="number" name="landlordid"  value="${landlordid}" hidden>
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label style="">Username</label>
            </div>
            <div class="col-75">
                <input type="text" name="landlordUsername" placeholder="Enter your username" value="">
            </div>
        </div>
        <div class="row">
            <div class="col-25">
                <label>Password</label>
            </div>
            <div class="col-75">
                <input type="password" name="landlordPassword" placeholder="Your password" value="">
            </div>
        </div>
        <p>Don't have an account?</p>
        <button type="submit" class="button button1" name="submit">Login</button>

        <button type="submit" class="button2 button1 " name="submit" ><a href ="landlord-createProfile.jsp">Sign up</a></button><br><br>

    </div>

    <%
    String mytext = request.getParameter("landlordid");

    if(mytext == null){

    }
    else{
    session.setAttribute("LAid",request.getParameter("landlordid"));
    }
    %>
</form>
</body>
</html>
