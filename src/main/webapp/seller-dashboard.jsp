<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<title>Seller Dashboard - Deepa Mart</title>

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
    max-width:900px;
    margin:30px auto;
}
.card{
    background:white;
    padding:25px;
    margin-bottom:20px;
    border-radius:10px;
    box-shadow:0 2px 8px #ccc;
}
h2{
    margin-top:0;
}
.btn{
    display:inline-block;
    padding:12px 20px;
    margin:8px 5px 0 0;
    background:#222;
    color:white;
    text-decoration:none;
    border-radius:6px;
}
.btn:hover{
    background:#444;
}
</style>
</head>

<body>

<div class="header">
    <h1>Deepa Mart</h1>
    <p>Seller Dashboard</p>
</div>

<div class="container">

<div class="card">
    <h2>Welcome, Seller!</h2>
    <p>Manage your products and customer orders.</p>
</div>

<div class="card">
    <h2>Product Management</h2>
    <p>Add and manage available products.</p>

    <a href="add-product.jsp" class="btn">Add Product</a>
    <a href="products" class="btn">View Products</a>
</div>

<div class="card">
    <h2>Order Management</h2>
    <p>View customer orders.</p>

    <a href="my-orders.jsp" class="btn">View Orders</a>
</div>

</div>

</body>
</html>