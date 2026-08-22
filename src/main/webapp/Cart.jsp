<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart - Deepa Mart</title>

<style>
*{box-sizing:border-box}
body{margin:0;font-family:Arial;background:#f5f5f5}
.header{background:#232f3e;color:white;padding:15px 30px;display:flex;justify-content:space-between;align-items:center}
.logo{font-size:28px;font-weight:bold}
button{border:none;cursor:pointer;font-weight:bold}
.back-btn,.checkout-btn{background:#ffd814;padding:12px 20px;border-radius:20px}
.main{max-width:900px;margin:auto;padding:30px}
.cart-container{background:white;padding:25px;border-radius:12px;box-shadow:0 2px 8px #ccc}
.cart-item{display:flex;justify-content:space-between;align-items:center;padding:18px 0;border-bottom:1px solid #ddd}
.item-name{font-size:20px;font-weight:bold}
.item-price{margin-top:5px}
.remove-btn{background:#dc3545;color:white;padding:8px 14px;border-radius:15px}
.total{text-align:right;font-size:24px;font-weight:bold;margin-top:25px}
.checkout-btn{width:100%;margin-top:20px;font-size:18px}
.empty-cart{text-align:center;padding:40px;font-size:20px;color:#666}
</style>
</head>

<body>

<div class="header">
    <div class="logo">🛒 Deepa Mart</div>
    <button class="back-btn" onclick="location.href='products'">
        ← Continue Shopping
    </button>
</div>

<div class="main">
    <h1>Your Shopping Cart 🛒</h1>
    <div class="cart-container" id="cartContainer"></div>
</div>

<script>
let cart=JSON.parse(localStorage.getItem("cart"))||[];

function displayCart(){
    let container=document.getElementById("cartContainer");

    if(cart.length===0){
        container.innerHTML='<div class="empty-cart">Your cart is empty! 🛒</div>';
        return;
    }

    let html="",total=0;

    for(let i=0;i<cart.length;i++){
        total+=Number(cart[i].price);
        html+='<div class="cart-item"><div><div class="item-name">'
            +cart[i].name+'</div><div class="item-price">₹'
            +cart[i].price+'</div></div><button class="remove-btn" onclick="removeFromCart('
            +i+')">Remove</button></div>';
    }

    container.innerHTML=html+
        '<div class="total">Total: ₹'+total+'</div>'+
        '<button class="checkout-btn" onclick="checkout()">Proceed to Checkout</button>';
}

function removeFromCart(i){
    cart.splice(i,1);
    localStorage.setItem("cart",JSON.stringify(cart));
    displayCart();
}

function checkout(){
    if(cart.length===0){
        alert("Your cart is empty!");
        return;
    }
    location.href="checkout.jsp";
}

displayCart();
</script>

</body>
</html>