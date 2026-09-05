<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.model.Order" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Orders - Deepa Mart</title>

<style>
body{
    font-family:Arial;
    margin:0;
    background:#f5f5f5;
}
.header{
    background:#222;
    color:white;
    padding:20px;
    text-align:center;
}
.container{
    width:90%;
    margin:30px auto;
}
table{
    width:100%;
    border-collapse:collapse;
    background:white;
}
th,td{
    padding:14px;
    border:1px solid #ddd;
    text-align:center;
}
th{
    background:#222;
    color:white;
}
.status{
    font-weight:bold;
}
.back{
    display:inline-block;
    margin-bottom:20px;
    text-decoration:none;
}
</style>
</head>

<body>

<div class="header">
    <h1>Customer Orders</h1>
</div>

<div class="container">

<a class="back" href="seller-dashboard.jsp">← Back to Dashboard</a>

<table>
<tr>
    <th>Order ID</th>
    <th>Total Amount</th>
    <th>Payment Method</th>
    <th>Status</th>
    <th>Update</th>
</tr>

<%
List<Order> orders =
    (List<Order>) request.getAttribute("orders");

if (orders != null && !orders.isEmpty()) {

    for (Order order : orders) {
%>

<tr>
    <td><%= order.getOrderId() %></td>
    <td>₹<%= order.getTotalAmount() %></td>
    <td><%= order.getPaymentMethod() %></td>
    <td class="status"><%= order.getOrderStatus() %></td>
    <td>
    <form action="update-order-status" method="post">

        <input type="hidden"
               name="orderId"
               value="<%= order.getOrderId() %>">

        <select name="status">
            <option value="Pending">Pending</option>
            <option value="Confirmed">Confirmed</option>
            <option value="Shipped">Shipped</option>
            <option value="Delivered">Delivered</option>
        </select>

        <button type="submit">Update</button>

    </form>
</td>
</tr>

<%
    }

} else {
%>

<tr>
    <td colspan="5">No orders found.</td>
</tr>

<%
}
%>

</table>

</div>

</body>
</html>