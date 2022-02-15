<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--
  Created by IntelliJ IDEA.
  User: TOSHIBA
  Date: 31/12/2021
  Time: 10:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>HOUSE RENTAL SYSTEM</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="tenant-viewPayment.css">
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
    SELECT  P.PAYID, P.PAYDUEDATE, P.PAYDATE, P.PAYRECEIPT, P.PAYSTATUS, P.BOOKINGID, P.PAYPRICE,T.TENANTID,T.TENANTNAME
    from TENANT T
    JOIN BOOKINGDETAILS B
    on T.TENANTID = B.TENANTID
    join MONTHLYPAYMENT P
    on B.BOOKINGID = P.BOOKINGID
    WHERE T.TENANTID =?
    <sql:param value="${clsid}" />
</sql:query>

<div class="container">
    <h3>MONTHLY PAYMENT</h3>
    <c:forEach var="result" items="${oc.rows}">
        <input type="hidden" name="payId" value="${result.payId}">
    <div class="row">
        <div class="col-25">
            <label>BOOKING ID</label>
        </div>
        <div class="col-75">
            <label>${result.bookingID}</label>
        </div>
    </div>

    <div class="row">
        <div class="col-25">
            <label>DUE DATE</label>
        </div>
        <div class="col-75">
            <label>${result.payduedate}</label>
        </div>
    </div>

    <div class="row">
        <div class="col-25">
            <label>TOTAL PRICE</label>
        </div>
        <div class="col-75">
            <label>${result.payprice}</label>
        </div>
    </div>

    <div class="row">
        <div class="col-25">
            <label>STATUS</label>
        </div>
        <div class="col-75">
            <label>${result.paystatus}</label>
        </div>
    </div>
    <br><br>
    <table id = "myTable">
        <tr>
            <th class="hello">NO.</th>
            <th>TENANT ID</th>
            <th>TENANT NAME</th>
            <th>RECEIPT</th>
        </tr>

        <tr>
            <td class="hello">1.</td>
            <td>${result.tenantID}</td>
            <td>${result.tenantname}</td>

            <form method="post" action="MonthlyPaymentServlet" enctype="multipart/form-data">
                <input type="hidden" name="payId" value="${result.payId}">
                <td>
                    <c:set var="status" value="${result.paystatus}"/>
                    <c:if test="${status=='unpaid'}">

                        <input type="file" name="payreceipt">
                        <a href="${result.payreceipt}" onclick="window.open('${result.payreceipt}', '_blank', 'fullscreen=yes'); return false;">${result.payreceipt}</a>

                        <input type="hidden" name="payId" value="${result.payId}">
                            <input type="hidden" name="action" value="update">
                            <button type="submit"  class="button button1" name="submit" >Update</button>

                    </c:if>
                </td>

            </form>
            </tr>

    </table>

    <button type="submit" class="button button1" name="submit" ><a href="tenant-listPayment.jsp">Back</a></button>
    </c:forEach>

</div>
</body>
</html>