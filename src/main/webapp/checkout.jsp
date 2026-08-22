<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout - Deepa Mart</title>
<style>
*{box-sizing:border-box}body{margin:0;font-family:Arial;background:#f5f5f5}
.header{background:#232f3e;color:#fff;padding:15px 30px;display:flex;justify-content:space-between}
.logo{font-size:28px;font-weight:bold}.main{max-width:900px;margin:auto;padding:30px}
.box{background:#fff;padding:25px;margin-bottom:20px;border-radius:12px;box-shadow:0 2px 8px #ccc}
button{border:none;cursor:pointer;font-weight:bold;background:#ffd814;padding:12px 20px;border-radius:20px}
.item{display:flex;justify-content:space-between;padding:12px;border-bottom:1px solid #ddd}
.total{text-align:right;font-size:22px;font-weight:bold;margin-top:15px}
.payment label{display:block;padding:12px;margin:8px 0;border:1px solid #ddd;border-radius:8px}
.confirm-btn{width:100%;font-size:18px;margin-top:15px}
.empty{text-align:center;padding:25px;color:#666}
</style>
</head>
<body>

<div class="header">
<div class="logo">🛒 Deepa Mart</div>
<button onclick="location.href='cart.jsp'">← Back to Cart</button>
</div>

<div class="main">
<h1>Checkout 🛍️</h1>

<div class="box">
<h2>Order Summary</h2>
<div id="orderItems"></div>
<div class="total" id="total"></div>
</div>

<div class="box">
<h2>Payment Method 💳</h2>
<div class="payment">
<label><input type="radio" name="payment" value="COD" checked> Cash on Delivery</label>
<label><input type="radio" name="payment" value="UPI"> UPI (Demo)</label>
<label><input type="radio" name="payment" value="CARD"> Card (Demo)</label>
</div>
<button class="confirm-btn" onclick="placeOrder()">Confirm Order</button>
</div>
</div>

<script>
let cart=JSON.parse(localStorage.getItem("cart"))||[];

function displayOrder(){
 let c=document.getElementById("orderItems"),t=0,h="";
 if(!cart.length){c.innerHTML='<div class="empty">Your cart is empty!</div>';return;}
 cart.forEach(x=>{
  let p=Number(x.price);t+=p;
  h+='<div class="item"><b>'+x.name+'</b><span>₹'+p+'</span></div>';
 });
 c.innerHTML=h;
 document.getElementById("total").innerHTML="Total: ₹"+t;
}

function placeOrder(){
 if(!cart.length){alert("Your cart is empty!");return;}
 let f=document.createElement("form"),p=document.querySelector('input[name="payment"]:checked').value;
 f.method="POST";f.action="place-order";
 f.innerHTML='<input type="hidden" name="paymentMethod" value="'+p+'">';
 cart.forEach(x=>{
  f.innerHTML+='<input type="hidden" name="productName" value="'+x.name+'">';
  f.innerHTML+='<input type="hidden" name="productPrice" value="'+x.price+'">';
 });
 document.body.appendChild(f);f.submit();
}

displayOrder();
</script>

</body>
</html>