<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.model.Order" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Orders - Deepa Mart</title>

<style>
*{
    box-sizing:border-box;
}

body{
    margin:0;
    font-family:Arial;
    background:#f5f5f5;
}

.header{
    background:#232f3e;
    color:white;
    padding:15px 30px;
    display:flex;
    justify-content:space-between;
    align-items:center;
}

.logo{
    font-size:28px;
    font-weight:bold;
}

button{
    border:none;
    background:#ffd814;
    padding:10px 18px;
    border-radius:20px;
    font-weight:bold;
    cursor:pointer;
}

.main{
    max-width:900px;
    margin:auto;
    padding:30px;
}

h1{
    color:#222;
}

.box{
    background:white;
    padding:20px;
    border-radius:12px;
    box-shadow:0 2px 8px #ccc;
    margin-bottom:18px;
}

.order{
    border-left:5px solid #232f3e;
}

.order-top{
    display:flex;
    justify-content:space-between;
    align-items:center;
}

.order-info{
    line-height:1.8;
}

.order-price{
    font-size:22px;
    font-weight:bold;
}

.status{
    display:inline-block;
    padding:5px 12px;
    border-radius:15px;
    background:#e7f3ff;
    color:#0066cc;
    font-weight:bold;
}

/* Status Progress */

.progress{
    display:flex;
    justify-content:space-between;
    margin:25px 10px 10px;
    position:relative;
}

.progress:before{
    content:"";
    position:absolute;
    top:15px;
    left:8%;
    right:8%;
    height:4px;
    background:#ddd;
    z-index:0;
}

.step{
    text-align:center;
    position:relative;
    z-index:1;
    width:25%;
}

.circle{
    width:30px;
    height:30px;
    margin:auto;
    border-radius:50%;
    background:#ddd;
    display:flex;
    align-items:center;
    justify-content:center;
    font-weight:bold;
}

.step.active .circle{
    background:#ffd814;
    color:#222;
}

.step p{
    font-size:12px;
    margin-top:8px;
    font-weight:bold;
}

.details-btn{
    background:#ffa41c;
    margin-top:15px;
}

.empty{
    text-align:center;
    padding:40px;
    color:#666;
}
</style>
</head>

<body>

<div class="header">

    <div class="logo">
        🛒 Deepa Mart
    </div>

    <button onclick="location.href='products'">
        Continue Shopping
    </button>

</div>

<div class="main">

<h1>My Orders 📦</h1>

<%
List<Order> orders =
    (List<Order>) request.getAttribute("orders");

if(orders == null || orders.isEmpty()){
%>

<div class="box empty">

    <h2>No orders found!</h2>

    <p>Start shopping and place your first order.</p>

    <button onclick="location.href='products'">
        Shop Now
    </button>

</div>

<%
}else{

for(Order order : orders){

    String currentStatus =
        order.getOrderStatus();

    int progress = 1;

    if("Confirmed".equalsIgnoreCase(currentStatus)){
        progress = 2;
    }
    else if("Shipped".equalsIgnoreCase(currentStatus)){
        progress = 3;
    }
    else if("Delivered".equalsIgnoreCase(currentStatus)){
        progress = 4;
    }
%>

<div class="box order">

    <div class="order-top">

        <div class="order-info">

            <b>Order #<%=order.getOrderId()%></b>

            <br>

            Payment Method:
            <%=order.getPaymentMethod()%>

            <br>

            Status:

            <span class="status">
                <%=order.getOrderStatus()%>
            </span>

        </div>

        <div class="order-price">
            ₹<%=String.format("%.2f",
                    order.getTotalAmount())%>
        </div>

    </div>


    <!-- ORDER PROGRESS -->

    <div class="progress">

        <div class="step
            <%=progress >= 1 ? "active" : ""%>">

            <div class="circle">1</div>
            <p>Pending</p>

        </div>


        <div class="step
            <%=progress >= 2 ? "active" : ""%>">

            <div class="circle">2</div>
            <p>Confirmed</p>

        </div>


        <div class="step
            <%=progress >= 3 ? "active" : ""%>">

            <div class="circle">3</div>
            <p>Shipped</p>

        </div>


        <div class="step
            <%=progress >= 4 ? "active" : ""%>">

            <div class="circle">4</div>
            <p>Delivered</p>

        </div>

    </div>


    <button class="details-btn"
            onclick="location.href='order-details?orderId=<%=order.getOrderId()%>'">

        View Details

    </button>

</div>

<%
}
}
%>

</div>

</body>
</html>