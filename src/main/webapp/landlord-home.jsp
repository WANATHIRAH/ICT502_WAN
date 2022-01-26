<!DOCTYPE html>
<html>
<title>NR RENTAL SYSYTEMM</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>

    body,h1,h2,h3,h4,h5,h6
    {
        font-family: "Raleway", Arial, Helvetica, sans-serif
    }

    h1
    {
        position: relative;
        text-align: center;
        color: white;


    }


</style>
<body class="w3-light-grey">
        <%@include file="landlord-navbar.html"%>

        <!-- Header -->
        <header class="w3-display-container w3-content w3-hide-small" style="max-width:1500px">
            <img class="house.jpg" src="house.jpg"  width="1500" height="700">

            <div style="margin-top:20px;">
            </div>
        </header>

        <div class="navbar">

            <a class="icon" href="landlord-home.jsp" >UITM NR RENTAL SYSTEM</a>


            <div class="dropdwn">
                <button class="dropbtn" >
                    <i class="fa fa-user"></i>
                </button>
                <div class="dropdwn-content" style="right: 0px; ">
                    <a href="landlord-viewProfile.jsp">PROFILE</a>
                    <a href="landlord-createProfile.jsp" ><i class="fa fa-sign-out"></i> LOGOUT</a>
                </div>
            </div>



            <div class="dropdwn">
                <button class="dropbtn" >LIST
                    <i class="fa fa-caret-down"></i>
                </button>
                <div class="dropdwn-content" style="right: 44px;">
                    <a href="landlord-displayHouseList.jsp">MY HOUSE</a>
                    <a href="landlord-listPayment.jsp">MY PAYMENT</a>
                    <a href="landlord-displayPayment.jsp">CONTACT US</a>
                </div>
            </div>




            <a href="landlord-home.jsp" >HOME</a>


        </div>


</body>
</html>