<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.Product" %>
<%@ page import="com.deepamart.model.Review" %>
<%@ page import="com.deepamart.ReviewDAO" %>

<%!
public String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;").replace("<","&lt;")
            .replace(">","&gt;").replace("\"","&quot;")
            .replace("'","&#39;");
}
%>

<%
List<Product> products=(List<Product>)request.getAttribute("products");
String role=(String)session.getAttribute("role");
if(products==null) products=new java.util.ArrayList<>();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Products - Deepa Mart</title>

<style>
*{box-sizing:border-box}
body{margin:0;font-family:Arial,sans-serif;background:#f3f6fb;color:#222}
.header{background:#102a43;color:white;padding:18px 35px;display:flex;justify-content:space-between;align-items:center;box-shadow:0 3px 10px #bbb}
.logo{font-size:24px;font-weight:bold}
.nav a{color:white;text-decoration:none;margin-left:18px;font-size:14px;font-weight:bold}
.nav a:hover{color:#ffd166}
.search{text-align:center;background:white;padding:25px}
.search input{width:60%;max-width:550px;padding:13px 18px;border:2px solid #d9e2ec;border-radius:25px;font-size:15px;outline:none}
.search input:focus{border-color:#2f80ed}
.message{position:fixed;top:80px;right:20px;background:#102a43;color:white;padding:13px 18px;border-radius:7px;display:none;z-index:10}
.message.show{display:block}
.products{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:24px;padding:30px 35px}
.card{background:white;padding:17px;border-radius:12px;box-shadow:0 3px 12px #d0d7de;transition:.2s}
.card:hover{transform:translateY(-4px);box-shadow:0 6px 18px #b8c4d0}
.card img{width:100%;height:165px;object-fit:contain;border-radius:8px}
.card h3{margin:12px 0 6px;color:#102a43;font-size:20px}
.category{color:#718096;font-size:14px}
.price{color:#d97706;font-size:20px;font-weight:bold;margin:12px 0}
button,.btn{border:0;padding:9px 12px;border-radius:6px;cursor:pointer;text-decoration:none;display:inline-block;margin:3px;font-weight:bold;font-size:13px}
.cart-btn{background:#ffd166;color:#102a43}
.cart-btn:hover{background:#f4b942}
.review{background:#2a9d8f;color:white}
.edit{background:#f4b942;color:#222}
.delete{background:#e63946;color:white}
.reviews{margin-top:15px;padding-top:10px;border-top:1px solid #e2e8f0}
.review-item{font-size:13px;margin:7px 0;color:#52606d;line-height:1.5}
.stars{color:#f4b942;font-weight:bold}
footer{text-align:center;padding:20px;background:#102a43;color:white}
@media(max-width:600px){
.header{padding:15px;flex-direction:column;gap:15px}
.logo{font-size:21px}.nav a{margin-left:7px;font-size:12px}
.search input{width:90%}.products{padding:20px}
}
</style>
</head>

<body>

<div class="header">
<div class="logo">🛒 DEEPA MART</div>
<div class="nav">
<a href="<%=request.getContextPath()%>/products">Products</a>

<%if("BUYER".equalsIgnoreCase(role)){%>
<a href="<%=request.getContextPath()%>/my-orders">My Orders</a>
<a href="<%=request.getContextPath()%>/cart">Cart (<span id="cartCount">0</span>)</a>
<%}%>

<%if("SELLER".equalsIgnoreCase(role)){%>
<a href="<%=request.getContextPath()%>/seller-dashboard.jsp">Dashboard</a>
<%}%>

<%if("ADMIN".equalsIgnoreCase(role)){%>
<a href="<%=request.getContextPath()%>/admin-dashboard.jsp">Dashboard</a>
<%}%>
</div>
</div>

<div id="message" class="message"></div>

<div class="search">
<input type="text" id="searchBox" placeholder="Search products..." onkeyup="searchProducts()">
</div>

<div class="products" id="productList">

<%
for(Product product:products){
String productName=esc(product.getProductName());
String category=esc(product.getCategory());
String imagePath=product.getImageUrl();

if(imagePath==null||imagePath.trim().isEmpty())
imagePath="images/default.jpg";
else if(!imagePath.startsWith("images/"))
imagePath="images/"+imagePath;

imagePath=esc(imagePath);
int productId=product.getProductId();
double price=product.getPrice();
%>

<div class="card product-card" data-name="<%=productName%>" data-category="<%=category%>">

<img src="<%=request.getContextPath()%>/<%=imagePath%>"
alt="<%=productName%>"
onerror="this.src='<%=request.getContextPath()%>/images/default.jpg'">

<h3><%=productName%></h3>
<div class="category">Category: <%=category%></div>
<div class="price">₹<%=price%></div>

<%if("BUYER".equalsIgnoreCase(role)){%>

<button class="cart-btn" type="button"
data-id="<%=productId%>"
data-name="<%=productName%>"
data-price="<%=price%>"
onclick="addToCart(this)">Add to Cart</button>

<a class="btn review" href="<%=request.getContextPath()%>/review?productId=<%=productId%>">Review</a>

<%}%>

<%if("SELLER".equalsIgnoreCase(role)||"ADMIN".equalsIgnoreCase(role)){%>

<a class="btn edit" href="<%=request.getContextPath()%>/edit-product?id=<%=productId%>">Edit</a>

<a class="btn delete" href="<%=request.getContextPath()%>/delete-product?id=<%=productId%>" onclick="return confirm('Delete this product?')">Delete</a>

<%}%>

<div class="reviews"><b>Reviews</b>

<%
try{
ReviewDAO reviewDAO=new ReviewDAO();
List<Review> reviews=reviewDAO.getReviewsByProduct(productId);

if(reviews!=null&&!reviews.isEmpty()){
for(Review review:reviews){
%>

<div class="review-item">
<span class="stars"><%=review.getRating()%> ★</span> -
<%=esc(review.getReviewText())%>
</div>

<%
}
}else{
%>
<div class="review-item">No reviews yet.</div>
<%
}
}catch(Exception e){
%>
<div class="review-item">Reviews unavailable.</div>
<%
}
%>

</div>
</div>

<%
}
%>

</div>

<footer>© 2026 Deepa Mart | All Rights Reserved</footer>

<script>
let cart=JSON.parse(localStorage.getItem("cart"))||[];

function updateCartCount(){
let count=document.getElementById("cartCount");
if(count)count.innerText=cart.length;
}

function addToCart(button){
let id=Number(button.dataset.id);
let name=button.dataset.name;
let price=Number(button.dataset.price);

cart.push({id:id,name:name,price:price});
localStorage.setItem("cart",JSON.stringify(cart));
updateCartCount();

let message=document.getElementById("message");
message.innerText=name+" added to cart ✓";
message.classList.add("show");

setTimeout(function(){
message.classList.remove("show");
},2000);
}

function searchProducts(){
let text=document.getElementById("searchBox").value.toLowerCase();
let cards=document.querySelectorAll(".product-card");

cards.forEach(function(card){
let name=card.dataset.name.toLowerCase();
let category=card.dataset.category.toLowerCase();

card.style.display=(name.includes(text)||category.includes(text))?"":"none";
});
}

updateCartCount();
</script>

</body>
</html>