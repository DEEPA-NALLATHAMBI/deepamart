<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.Product" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deepa Mart</title>
<style>
*{box-sizing:border-box}
body{margin:0;font-family:Arial;background:#f5f5f5}
.header{background:#232f3e;color:white;padding:15px 30px;display:flex;align-items:center;gap:25px}
.logo{font-size:28px;font-weight:bold;white-space:nowrap}
.search-box{flex:1;display:flex}
.search-box input{width:100%;padding:12px;border:none;outline:none;font-size:16px}
.search-box button{padding:12px 20px;border:none;cursor:pointer;font-weight:bold}
.cart{font-size:18px;cursor:pointer;white-space:nowrap}
.main{padding:25px 40px}
.title{margin-bottom:25px;font-size:28px}
.products{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:22px}
.product{background:white;border-radius:12px;padding:18px;box-shadow:0 2px 8px rgba(0,0,0,.12);transition:.2s}
.product:hover{transform:translateY(-5px)}
.product img{width:100%;height:180px;object-fit:contain;margin-bottom:12px}
.product h3{margin:8px 0;font-size:20px}
.category{color:#666;margin:5px 0}
.price{font-size:22px;font-weight:bold;margin:10px 0}
.stock{color:green;margin-bottom:15px}
.add-cart{width:100%;padding:12px;border:none;border-radius:20px;background:#ffd814;cursor:pointer;font-weight:bold}
.add-cart:hover{background:#f7ca00}
#message{position:fixed;bottom:25px;right:25px;background:#232f3e;color:white;padding:15px 22px;border-radius:8px;font-weight:bold;opacity:0;transform:translateY(20px);transition:.3s}
#message.show{opacity:1;transform:translateY(0)}
footer{margin-top:40px;padding:20px;text-align:center;background:#232f3e;color:white}
</style>
</head>

<body>

<div class="header">
    <div class="logo">🛒 Deepa Mart</div>
    <div class="search-box">
        <input type="text" id="searchInput" placeholder="Search products...">
        <button onclick="searchProducts()">Search</button>
    </div>
    <div class="cart" onclick="showCart()">
        🛒 Cart (<span id="cartCount">0</span>)
    </div>
</div>

<div class="main">
<h1 class="title">Shop Products</h1>
<div class="products">

<%
List<Product> products=(List<Product>)request.getAttribute("products");
if(products!=null){
    for(Product product:products){
%>

<div class="product">
    <img src="/deepa-mart/<%=product.getImageUrl()%>"
         alt="<%=product.getProductName()%>">

    <h3><%=product.getProductName()%></h3>
    <p class="category"><%=product.getCategory()%></p>
    <p class="price">₹<%=product.getPrice()%></p>
    <p class="stock">In Stock: <%=product.getStock()%></p>

    <button class="add-cart"
    onclick="addToCart(<%=product.getProductId()%>,
    '<%=product.getProductName()%>',<%=product.getPrice()%>)">
    Add to Cart
    </button>
</div>

<%
    }
}
%>

</div>
</div>

<footer>© 2026 Deepa Mart | Happy Shopping 🛒</footer>

<div id="message"></div>

<script>
let cart=JSON.parse(localStorage.getItem("cart"))||[];
document.getElementById("cartCount").innerText=cart.length;

function addToCart(id,name,price){
    cart.push({id:id,name:name,price:price});
    localStorage.setItem("cart",JSON.stringify(cart));
    document.getElementById("cartCount").innerText=cart.length;

    let message=document.getElementById("message");
    message.innerText=name+" added to cart ✓";
    message.classList.add("show");

    setTimeout(function(){
        message.classList.remove("show");
    },2000);
}

function showCart(){
    window.location.href="Cart.jsp";
}

function searchProducts(){
    let filter=document.getElementById("searchInput").value.toLowerCase();
    let products=document.getElementsByClassName("product");

    for(let i=0;i<products.length;i++){
        let name=products[i].getElementsByTagName("h3")[0]
        .innerText.toLowerCase();

        products[i].style.display=name.includes(filter)?"":"none";
    }
}
</script>

</body>
</html>