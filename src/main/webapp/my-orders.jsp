<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.model.Order" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Orders - Deepa Mart</title>

<style>
*{box-sizing:border-box}
body{margin:0;font-family:Arial;background:#f5f5f5}
.header{background:#232f3e;color:white;padding:15px 30px;
display:flex;justify-content:space-between}
.logo{font-size:28px;font-weight:bold}
button{border:none;background:#ffd814;padding:10px 18px;
border-radius:20px;font-weight:bold;cursor:pointer}
.main{max-width:900px;margin:auto;padding:30px}
.box{background:white;padding:20px;border-radius:12px;
box-shadow:0 2px 8px #ccc;margin-bottom:15px}
.order{display:flex;justify-content:space-between;
padding:12px;border-bottom:1px solid #ddd}
.empty{text-align:center;padding:30px;color:#666}
</style>
</head>

<body>

<div class="header">
<div class="logo">🛒 Deepa Mart</div>
<button onclick="location.href='products'">Continue Shopping</button>
</div>

<div class="main">
<h1>My Orders 📦</h1>

<%
List<Order> orders=(List<Order>)request.getAttribute("orders");

if(orders==null || orders.isEmpty()){
%>

<div class="box empty">No orders found!</div>

<%
}else{
for(Order order:orders){
%>

<div class="box order">
<div>
<b>Order #<%=order.getOrderId()%></b><br>
Payment: <%=order.getPaymentMethod()%><br>
Status: <%=order.getOrderStatus()%>
</div>

<div>
<b>₹<%=order.getTotalAmount()%></b>
</div>
</div>

<%
}
}
%>

</div>

</body>
</html>