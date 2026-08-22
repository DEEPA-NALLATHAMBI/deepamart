<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Successful - Deepa Mart</title>

<style>
*{box-sizing:border-box}

body{
    margin:0;
    font-family:Arial;
    background:#f5f5f5;
}

.header{
    background:#232f3e;
    color:white;
    padding:15px 30px;
    font-size:28px;
    font-weight:bold;
}

.main{
    max-width:700px;
    margin:60px auto;
    padding:30px;
}

.success-box{
    background:white;
    text-align:center;
    padding:50px 30px;
    border-radius:15px;
    box-shadow:0 2px 10px #ccc;
}

.icon{
    font-size:70px;
}

h1{
    color:#198754;
}

p{
    font-size:18px;
    color:#555;
}

.order-id{
    font-size:20px;
    font-weight:bold;
    margin:20px 0;
}

.shop-btn{
    background:#ffd814;
    border:none;
    padding:14px 25px;
    border-radius:25px;
    font-size:17px;
    font-weight:bold;
    cursor:pointer;
    margin-top:20px;
}
</style>
</head>

<body>

<div class="header">
    🛒 Deepa Mart
</div>

<div class="main">

    <div class="success-box">

        <div class="icon">✅</div>

        <h1>Order Placed Successfully!</h1>

        <p>
            Thank you for shopping with Deepa Mart.
        </p>

        <div class="order-id">
            Your Order ID:
            <%= request.getParameter("orderId") %>
        </div>

        <p>
            Your order is now being processed.
        </p>

        <button class="shop-btn" onclick="continueShopping()">
            Continue Shopping
        </button>

    </div>

</div>

<script>

function continueShopping(){

    localStorage.removeItem("cart");

    location.href = "products";
}

</script>

</body>
</html>