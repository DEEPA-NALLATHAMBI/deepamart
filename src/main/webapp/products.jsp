<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.deepamart.Product,com.deepamart.model.Review,com.deepamart.ReviewDAO" %>

<%!
public String esc(String s){
 if(s==null)return "";
 return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
}
%>

<%
List<Product> products=(List<Product>)request.getAttribute("products");
String role=(String)session.getAttribute("role");
if(products==null)products=new java.util.ArrayList<>();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Products - Deepa Mart</title>

<style>
*{box-sizing:border-box}
body{margin:0;font-family:Arial;background:#f4f6f9;color:#333}
.header{background:#252525;color:white;padding:18px 35px;display:flex;justify-content:space-between;align-items:center}
.logo{font-size:25px;font-weight:bold}
.nav a{color:white;text-decoration:none;margin-left:18px;font-size:14px}
.nav a:hover{color:#ffd43b}
.search{text-align:center;background:white;padding:25px}
.search input{width:60%;padding:13px 18px;border:1px solid #bbb;border-radius:25px;font-size:15px}
.message{position:fixed;top:80px;right:20px;background:#222;color:white;padding:12px 18px;border-radius:6px;display:none;z-index:5}
.message.show{display:block}
.products{display:grid;grid-template-columns:repeat(auto-fit,minmax(210px,1fr));gap:22px;padding:30px}
.card{background:white;padding:15px;border-radius:8px;box-shadow:0 2px 8px #ccc}
.card:hover{transform:translateY(-3px)}
.card img{width:100%;height:170px;object-fit:contain}
.card h3{margin:12px 0 6px;font-size:19px}
.category{color:#777;font-size:14px}
.price{font-size:18px;font-weight:bold;margin:9px 0}
.stock{font-size:14px;color:#555;margin-bottom:10px}
button,.btn{border:0;padding:9px 12px;border-radius:5px;cursor:pointer;text-decoration:none;display:inline-block;margin:3px 2px;font-size:12px;font-weight:bold}
.cart-btn{background:#f4c542;color:#222}
.review{background:#42b9c5;color:white}
.edit{background:#e9a34a;color:white}
.delete{background:#c92d63;color:white}
.reviews{margin-top:13px;padding-top:10px;border-top:1px solid #ddd}
.review-item{font-size:12px;margin:7px 0;line-height:1.5}
.stars{color:#e7a900}
footer{text-align:center;padding:20px;background:#252525;color:white}
@media(max-width:600px){.header{flex-direction:column;gap:15px;padding:15px}.nav a{margin-left:7px;font-size:12px}.search input{width:90%}.products{padding:20px}}
</style>
</head>

<body>

<div class="header">
<div class="logo">DEEPA MART</div>
<div class="nav">
<a href="<%=request.getContextPath()%>/products">Products</a>
<%if("BUYER".equalsIgnoreCase(role)){%>
<a href="<%=request.getContextPath()%>/my-orders">My Orders</a>
<a href="<%=request.getContextPath()%>/Cart.jsp">Cart (<span id="cartCount">0</span>)</a>
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
<input id="searchBox" type="text" placeholder="Search products..." onkeyup="searchProducts()">
</div>

<div class="products" id="productList">

<%for(Product product:products){
String name=esc(product.getProductName());
String category=esc(product.getCategory());
String image=product.getImageUrl();
if(image==null||image.trim().isEmpty())image="images/default.jpg";
else if(!image.startsWith("images/"))image="images/"+image;
image=esc(image);
int id=product.getProductId();
double price=product.getPrice();
int stock=product.getStock();
%>

<div class="card product-card" data-name="<%=name%>" data-category="<%=category%>">

<img src="<%=request.getContextPath()%>/<%=image%>" alt="<%=name%>" onerror="this.src='<%=request.getContextPath()%>/images/default.jpg'">

<h3><%=name%></h3>
<div class="category">Category: <%=category%></div>
<div class="price">&#8377;<%=price%></div>
<div class="stock">In Stock: <%=stock%></div>

<%if("BUYER".equalsIgnoreCase(role)){%>

<button class="cart-btn" type="button" data-id="<%=id%>" data-name="<%=name%>" data-price="<%=price%>" onclick="addToCart(this)">Add to Cart</button>

<a class="btn review" href="<%=request.getContextPath()%>/review?productId=<%=id%>">Review</a>

<%}%>

<%if("SELLER".equalsIgnoreCase(role)||"ADMIN".equalsIgnoreCase(role)){%>

<a class="btn edit" href="<%=request.getContextPath()%>/edit-product?id=<%=id%>">Edit Product</a>

<a class="btn delete" href="<%=request.getContextPath()%>/delete-product?id=<%=id%>" onclick="return confirm('Delete this product?')">Delete Product</a>

<%}%>

<div class="reviews"><b>Reviews</b>

<%try{
ReviewDAO dao=new ReviewDAO();
List<Review> reviews=dao.getReviewsByProduct(id);
if(reviews!=null&&!reviews.isEmpty()){
for(Review review:reviews){
%>

<div class="review-item">
<span class="stars"><%=review.getRating()%> ★</span> -
<%=esc(review.getReviewText())%>
</div>

<%}}else{%>
<div class="review-item">No reviews yet.</div>
<%}}catch(Exception e){%>
<div class="review-item">Reviews unavailable.</div>
<%}%>

</div>
</div>

<%}%>
</div>

<footer>© 2026 Deepa Mart | All Rights Reserved</footer>

<script>
let cart=JSON.parse(localStorage.getItem("cart"))||[];

function updateCartCount(){
let count=document.getElementById("cartCount");
if(count)count.innerText=cart.length;
}

function addToCart(btn){
let item={
id:Number(btn.dataset.id),
name:btn.dataset.name,
price:Number(btn.dataset.price)
};
cart.push(item);
localStorage.setItem("cart",JSON.stringify(cart));
updateCartCount();

let msg=document.getElementById("message");
msg.innerText=item.name+" added to cart ✓";
msg.classList.add("show");

setTimeout(()=>msg.classList.remove("show"),2000);
}

function searchProducts(){
let text=document.getElementById("searchBox").value.toLowerCase();

document.querySelectorAll(".product-card").forEach(card=>{
let name=card.dataset.name.toLowerCase();
let category=card.dataset.category.toLowerCase();

card.style.display=name.includes(text)||category.includes(text)?"":"none";
});
}

updateCartCount();
</script>

</body>
</html>