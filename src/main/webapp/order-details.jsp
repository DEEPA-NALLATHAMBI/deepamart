<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.model.OrderItem" %>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Order Details - Deepa Mart</title>

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

.box{
    background:white;
    padding:20px;
    border-radius:12px;
    box-shadow:0 2px 8px #ccc;
    margin-bottom:20px;
}

.item{
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding:15px 5px;
    border-bottom:1px solid #ddd;
}

.product{
    font-size:18px;
    font-weight:bold;
}

.price{
    font-weight:bold;
    font-size:18px;
}

.quantity{
    color:#666;
    margin-top:5px;
}

.empty{
    text-align:center;
    color:#666;
    padding:40px;
}

.back{
    background:#ffa41c;
}

</style>

</head>

<body>

<div class="header">

    <div class="logo">
        🛒 Deepa Mart
    </div>

    <button onclick="location.href='my-orders'">
        My Orders
    </button>

</div>


<div class="main">

<h1>Order Details 📦</h1>

<div class="box">

    <h2>
        Order #<%=request.getAttribute("orderId")%>
    </h2>

<%
List<OrderItem> items =
    (List<OrderItem>) request.getAttribute("items");

if(items == null || items.isEmpty()){
%>

    <div class="empty">
        No product details found.
    </div>

<%
}else{

double total = 0;

for(OrderItem item : items){

    double itemTotal =
        item.getProductPrice() * item.getQuantity();

    total += itemTotal;
%>

    <div class="item">

        <div>

            <div class="product">
                <%=item.getProductName()%>
            </div>

            <div class="quantity">
                Quantity: <%=item.getQuantity()%>
            </div>

        </div>

        <div class="price">
            ₹<%=String.format("%.2f", itemTotal)%>
        </div>

    </div>

<%
}
%>

    <h2 style="text-align:right;">
        Total: ₹<%=String.format("%.2f", total)%>
    </h2>

<%
}
%>

</div>

<button class="back"
        onclick="location.href='my-orders'">
    ← Back to My Orders
</button>

</div>

</body>
</html>