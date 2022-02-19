<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: TOSHIBA
  Date: 31/12/2021
  Time: 2:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>HOUSE RENTAL SYSTEM</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="tenant-listBooking.css">
    <link href="https://emoji-css.afeld.me/emoji.css" rel="stylesheet">
</head>

<body>

<%@include file="tenant-navbar.html"%>

<%
    int tenantid = Integer.parseInt(session.getAttribute("tenantid").toString());
    System.out.println(tenantid);
%>
<sql:setDataSource var="ic"
                   driver="org.postgresql.Driver"
                   url="jdbc:postgresql://ec2-34-194-171-47.compute-1.amazonaws.com/dcb70s908sasfa"
                   user = "gpdkvocjaztxrw"
                   password="dceb52b9fa471dce9048a701a0f88b7d4dee9e9ca420a48101baa31d0e68def5"/>

<sql:query dataSource="${ic}" var="oc">
    <c:set var="clsid" value="<%=tenantid%>"/>
    SELECT  H.HOUSENAME, B.BOOKINGID, B.BOOKINGSTATUS, B.BOOKINGTIME, B.BOOKINGDATE, B.BOOKINGDEPO, B.BOOKINGAGREEMENT, B.BOOKINGAPPROVALDATE, B.TENANTID, B.HOUSEID, h.housename
    from TENANT t
    join BOOKINGDETAILS B
    on t.TENANTID = B.TENANTID
    join HOUSEDETAILS H
    on B.HOUSEID = H.HOUSEID
    WHERE t.TENANTID=?
    <sql:param value="${clsid}" />
</sql:query>


<div class="container" style="width: auto;">

    <table id = "myTable">

        <h3>LIST OF BOOKING</h3>

        <tr>
            <th>BOOKING ID</th>
            <th>HOUSE NAME</th>
            <th>TIME</th>
            <th>DATE</th>
            <th>DEPOSIT</th>
            <th>AGREEMENT </th>
            <th>APPROVAL DATE</th>
            <th>STATUS</th>
            <th>ACTION</th>
        </tr>

        <c:forEach var="result" items="${oc.rows}">
        <tr>

            <td>${result.bookingid}</td>
            <td>${result.housename}</td>
            <td>${result.bookingtime}</td>
            <td>${result.bookingdate}</td>

            <form method="post" action="BookingServlet" enctype="multipart/form-data">
            <td>
                <c:set var="status" value="${result.bookingstatus}"/>
                <c:if test="${status=='Pending'}">

                    <input type="file" name="bookingdepo">
                    <a href="${result.bookingDepo}" onclick="window.open('${result.bookingdepo}', '_blank', 'fullscreen=yes'); return false;">${result.bookingdepo}</a>


                </c:if>
            </td>

            <td>
                <c:set var="status" value="${result.bookingstatus}"/>
                <c:if test="${status=='Pending'}">
                   <input type="file" name="bookingagreement">
                   <a href="${result.bookingagreement}" onclick="window.open('${result.bookingagreement}', '_blank', 'fullscreen=yes'); return false;">${result.bookingagreement}</a><br><br>
                    <a href="${result.bookingagreement}"> click here to download the file ${result.bookingagreement}</a>

                </c:if>
            </td>


            <td>${result.bookingapprovaldate}</td>
            <td>${result.bookingstatus}</td>

            <td>


                    <input type="hidden" name="bookingid" value="${result.bookingid}">
                    <input type="hidden" name="action" value="update">
                    <button type="submit" class="button button1" name="submit" >Update</button><br><br>
            </form>

                <form method="post">
                    <input type="hidden" name="bookingid" value="${result.bookingid}">
                    <input type="hidden" name="action" value="delete">
                <button type="submit" class="button button1" name="submit" onclick="return confirm('Confirm delete Booking? House name: <c:out value="${result.housename}"/>');" formaction="BookingServlet">Delete</button><br><br>
                </form>

            </td>

        </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>