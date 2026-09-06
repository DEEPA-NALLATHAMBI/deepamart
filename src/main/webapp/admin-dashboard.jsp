<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard - Deepa Mart</title>

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
    padding:18px 30px;
    display:flex;
    justify-content:space-between;
    align-items:center;
}

.logo{
    font-size:28px;
    font-weight:bold;
}

.admin{
    font-size:16px;
}

.main{
    max-width:1000px;
    margin:auto;
    padding:30px;
}

h1{
    color:#222;
}

.welcome{
    background:white;
    padding:25px;
    border-radius:12px;
    box-shadow:0 2px 8px #ccc;
    margin-bottom:25px;
}

.cards{
    display:grid;
    grid-template-columns:repeat(auto-fit,minmax(250px,1fr));
    gap:20px;
}

.card{
    background:white;
    padding:25px;
    border-radius:12px;
    box-shadow:0 2px 8px #ccc;
    text-align:center;
}

.icon{
    font-size:45px;
}

.card h2{
    margin:15px 0 10px;
}

.card p{
    color:#666;
}

.btn{
    display:inline-block;
    margin-top:12px;
    padding:12px 22px;
    background:#232f3e;
    color:white;
    text-decoration:none;
    border-radius:20px;
    font-weight:bold;
}

.btn:hover{
    background:#444;
}

footer{
    margin-top:40px;
    padding:20px;
    text-align:center;
    background:#232f3e;
    color:white;
}
</style>

</head>

<body>

<div class="header">

    <div class="logo">
        🛒 Deepa Mart
    </div>

    <div class="admin">
        👤 Admin Dashboard
    </div>

</div>


<div class="main">

    <div class="welcome">

        <h1>Welcome, Admin! 👋</h1>

        <p>
            Manage users, customer orders and product listings
            from one place.
        </p>

    </div>


    <div class="cards">


        <!-- Users -->

        <div class="card">

            <div class="icon">
                👥
            </div>

            <h2>Manage Users</h2>

            <p>
                View registered buyers and sellers.
            </p>

            <a href="admin-users" class="btn">
                View Users
            </a>

        </div>


        <!-- Orders -->

        <div class="card">

            <div class="icon">
                📦
            </div>

            <h2>Manage Orders</h2>

            <p>
                View and manage customer orders.
            </p>

            <a href="seller-orders" class="btn">
                View Orders
            </a>

        </div>


        <!-- Products -->

        <div class="card">

            <div class="icon">
                🛍️
            </div>

            <h2>Manage Products</h2>

            <p>
                View and manage product listings.
            </p>

            <a href="products" class="btn">
                View Products
            </a>

        </div>


    </div>

</div>


<footer>

    © 2026 Deepa Mart | Admin Panel

</footer>

</body>
</html>