<%--
  Created by IntelliJ IDEA.
  User: MSI
  Date: 31/12/2021
  Time: 9:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>


<html>
<head>
    <title>Landlord List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="admin-list.css">
    <link href="https://emoji-css.afeld.me/emoji.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    if(session.getAttribute("adminid")==null)
        response.sendRedirect("admin-login.jsp");
%>

<%@include file="admin-navbar.html"%>


<sql:setDataSource var="ic" driver="org.postgresql.Driver"
                   url="jdbc:postgresql://ec2-34-194-171-47.compute-1.amazonaws.com/dcb70s908sasfa"
                   user = "gpdkvocjaztxrw"
                   password="dceb52b9fa471dce9048a701a0f88b7d4dee9e9ca420a48101baa31d0e68def5"/>

<sql:query dataSource="${ic}" var="oc">
    SELECT row_number() over () "rank", landlordid, landlordusername, landlordpassword, landlordname, landlordemail, landlordage, landlordphoneno, landlordgender from landlord
</sql:query>


<div class="container">

    <h3>LIST OF LANDLORD</h3>


    <table id = "myTable">


        <tr>
            <th class="no">NO.</th>
            <th class="name"> NAME</th>
            <th class="name"> EMAIL</th>
            <th class="name"> AGE</th>
            <th class="name"> PHONE NO</th>
            <th class="name"> GENDER</th>
            <th class="name"> ACTION</th>

        </tr>
        <c:forEach var="result" items="${oc.rows}">
        <tr>
            <td class="no"><c:out value="${result.rank}"/></td>
            <td class="name"><c:out value="${result.landlordname}"/></td>
            <td class="no"><c:out value="${result.landlordemail}"/></td>
            <td class="id"><c:out value="${result.landlordage}"/></td>
            <td class="name"><c:out value="${result.landlordphoneno}"/></td>
            <td class="name"><c:out value="${result.landlordgender}"/></td>
            <td>
                <form method="post">
                    <input type="hidden" name="tenantid" value="${result.landlordid}">
                    <input type="hidden" name="action" value="removelandlord">
                    <button type="submit" formaction="AdminServlet"
                            onclick="return confirm('Confirm delete <c:out value="${result.landlordname}"/> ?');">Delete
                    </button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
