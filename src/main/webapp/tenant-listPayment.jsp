<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--
  Created by IntelliJ IDEA.
  User: TOSHIBA
  Date: 31/12/2021
  Time: 10:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>HOUSE RENTAL SYSTEM</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="tenant-listPayment.css">
    <link href="https://emoji-css.afeld.me/emoji.css" rel="stylesheet">
</head>

<body>
<%@include file="tenant-navbar.html"%>

<%
    int tenantid = Integer.parseInt(session.getAttribute("tenantid").toString());

%>
<sql:setDataSource var="ic"
                   driver="org.postgresql.Driver"
                   url="jdbc:postgresql://ec2-34-194-171-47.compute-1.amazonaws.com/dcb70s908sasfa"
                   user="gpdkvocjaztxrw"
                   password="dceb52b9fa471dce9048a701a0f88b7d4dee9e9ca420a48101baa31d0e68def5"/>

<sql:query dataSource="${ic}" var="oc">
    <c:set var="clsid" value="<%=tenantid%>"/>
    SELECT  row_number() over () "rank", B.bookingid,b.landlordid, b.rentalstatus, h.housename, l.landlordname
from TENANT T
JOIN BOOKINGDETAILS B
    on T.TENANTID = B.TENANTID
    join landlord l
    on B.landlordid = l.landlordid
    join housedetails h
    on l.landlordid = h.landlordid
    WHERE T.TENANTID =?
    and b.rentalstatus in ('On Going','Completed')
    <sql:param value="${clsid}" />
</sql:query>

<div class="container">
    <table id = "myTable" style="text-align: center">
        <h3>LIST OF PAYMENT</h3>
        <tr>
            <th class="hello">NO.</th>
            <th>HOUSE NAME</th>
            <th>LANDLORD NAME</th>
            <th>RENTAL STATUS</th>
            <th> </th>
            <th> </th>
        </tr>
        <c:forEach var="result" items="${oc.rows}">
        <tr>
            <td class="hello">${result.rank}</td>
            <td>${result.housename}</td>
            <td>${result.landlordname}</td>

            <c:set var="status" value="${result.rentalstatus}"/>
            <c:if test="${status=='On Going'}">
                <td style="color: forestgreen">${result.rentalstatus}</td>
            </c:if>
            <c:if test="${status=='Completed'}">
                <td style="color: dodgerblue">${result.rentalstatus}</td>
            </c:if>
            <input type="hidden" name="bookingid" value="${result.bookingid}">
            <input type="hidden" name="landlordid" value="${result.landlordid}">
            <td><button type="submit" class="button button1" name="submit" formaction="tenant-viewPayment.jsp" value="View More" ></button><br><br></td>
        </tr>
        </c:forEach>
    </table>
</div>



</body>
</html>