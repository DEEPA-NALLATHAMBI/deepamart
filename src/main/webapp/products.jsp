<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.Product" %>
<%@ page import="com.deepamart.model.Review" %>
<%@ page import="com.deepamart.ReviewDAO" %>

<%!
public String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;")
            .replace("<","&lt;")
            .replace(">","&gt;")
            .replace("\"","&quot;")
            .replace("'","&#39;");
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Deepa Mart</title>

<style>
*{box-sizing:border-box}
body{margin:0;font-family:Arial;background:#f5f5f5}

.header{
 background:#232f3e;color:white;padding:15px 30px;
 display:flex;align-items:center;gap:20px
}
.logo{font-size:28px;font-weight:bold;white-space:nowrap}
.search-box{flex:1;display:flex}
.search-box input{
 width:100%;padding:12px;border:none;outline:none;font-size:16px
}
.search-box button{
 padding:12px 20px;border:none;cursor:pointer;font-weight:bold
}
.nav-btn{
 background:#ffd814;border:none;padding:10px 16px;
 border-radius:20px;font-weight:bold;cursor:pointer
}
.cart{font-size:18px;cursor:pointer;white-space:nowrap}

.main{padding:25px 40px}
.title{margin-bottom:25px;font-size:28px}

.products{
 display:grid;
 grid-template-columns:repeat(auto-fit,minmax(220px,1fr));
 gap:22px
}
.product{
 background:white;border-radius:12px;padding:18px;
 box-shadow:0 2px 8px rgba(0,0,0,.12)
}
.product img{
 width:100%;height:180px;object-fit:contain;margin-bottom:12px
}
.product h3{margin:8px 0;font-size:20px}
.category{color:#666;margin:5px 0}
.price{font-size:22px;font-weight:bold;margin:10px 0}
.stock{color:green;margin-bottom:15px}

.add-cart,.delete-btn{
 width:100%;padding:11px;border:none;border-radius:20px;
 cursor:pointer;font-weight:bold
}
.add-cart{background:#ffd814}

.edit-btn,.review-btn{
 display:block;width:100%;padding:10px;margin-top:10px;
 border-radius:20px;text-align:center;text-decoration:none;
 font-weight:bold
}
.edit-btn{background:#ffa41c;color:black}
.delete-btn{background:#e53935;color:white;margin-top:10px}
.review-btn{background:#90caf9;color:#111}

.reviews{
 margin-top:15px;padding-top:10px;border-top:1px solid #ddd
}
.reviews h4{margin:5px 0 10px}
.review-box{
 background:#f5f5f5;padding:9px;margin-top:7px;border-radius:8px
}
.review-text{
 margin:5px 0;
 word-wrap:break-word
}
.no-review{color:#777;font-size:14px}

#message{
 position:fixed;bottom:25px;right:25px;
 background:#232f3e;color:white;padding:15px 22px;
 border-radius:8px;font-weight:bold;
 opacity:0;transition:.3s
}
#message.show{opacity:1}

footer{
 margin-top:40px;padding:20px;text-align:center;
 background:#232f3e;color:white
}
</style>
</head>

<body>

<%
String role = (String)session.getAttribute("role");
List<Product> products =
    (List<Product>)request.getAttribute("products");
%>

<div class="header">

    <div class="logo">🛒 Deepa Mart</div>

    <div class="search-box">
        <input type="text"
               id="searchInput"
               placeholder="Search products...">
        <button onclick="searchProducts()">Search</button>
    </div>

    <% if("BUYER".equalsIgnoreCase(role)){ %>

        <button class="nav-btn"
                onclick="location.href='my-orders'">
            📦 My Orders
        </button>

    <% } %>

    <div class="cart" onclick="showCart()">
        🛒 Cart (<span id="cartCount">0</span>)
    </div>

</div>

<div class="main">

<h1 class="title">Shop Products</h1>

<div class="products">

<%
if(products != null && !products.isEmpty()) {

    for(Product product : products) {

        String imagePath = product.getImageUrl();

        if(imagePath == null || imagePath.trim().isEmpty())
            imagePath = "images/default.jpg";
        else if(!imagePath.startsWith("images/"))
            imagePath = "images/" + imagePath;

        String productName = esc(product.getProductName());
        String category = esc(product.getCategory());
%>

<div class="product">

    <img src="<%=request.getContextPath()%>/<%=imagePath%>"
         alt="<%=productName%>">

    <h3><%=productName%></h3>

    <p class="category"><%=category%></p>

    <p class="price">₹<%=product.getPrice()%></p>

    <p class="stock">
        In Stock: <%=product.getStock()%>
    </p>

    <button class="add-cart"
            onclick="addToCart(
            <%=product.getProductId()%>,
            '<%=product.getProductName().replace("'","\\'")%>',
            <%=product.getPrice()%>)">
        Add to Cart
    </button>


    <!-- Seller / Admin -->

    <% if("SELLER".equalsIgnoreCase(role) ||
          "ADMIN".equalsIgnoreCase(role)) { %>

        <a class="edit-btn"
           href="edit-product.jsp?id=<%=product.getProductId()%>">
            Edit Product
        </a>

        <form action="delete-product"
              method="post"
              onsubmit="return confirm('Delete this product?');">

            <input type="hidden"
                   name="id"
                   value="<%=product.getProductId()%>">

            <button class="delete-btn" type="submit">
                Delete Product
            </button>

        </form>

    <% } %>


    <!-- Buyer Review -->

    <% if("BUYER".equalsIgnoreCase(role)) { %>

        <a class="review-btn"
           href="review.jsp?productId=<%=product.getProductId()%>">
            ⭐ Rate & Review
        </a>

    <% } %>


    <!-- Customer Reviews -->

    <%
    ReviewDAO reviewDAO = new ReviewDAO();
    List<Review> reviews =
        reviewDAO.getReviewsByProduct(product.getProductId());
    %>

    <div class="reviews">

        <h4>⭐ Customer Reviews</h4>

        <% if(reviews != null && !reviews.isEmpty()) {

            for(Review review : reviews) {
        %>

            <div class="review-box">

                <div>
                    <% for(int i=0;i<review.getRating();i++) { %>
                        ⭐
                    <% } %>
                </div>

                <!-- XSS PROTECTION -->
                <p class="review-text">
                    <%=esc(review.getReviewText())%>
                </p>

            </div>

        <%
            }

        } else {
        %>

            <p class="no-review">
                No reviews yet.
            </p>

        <% } %>

    </div>

</div>

<%
    }
} else {
%>

<p>No products available.</p>

<% } %>

</div>
</div>

<footer>
© 2026 Deepa Mart | Happy Shopping 🛒
</footer>

<div id="message"></div>

<script>

let cart = JSON.parse(localStorage.getItem("cart")) || [];

document.getElementById("cartCount").innerText = cart.length;

function addToCart(id,name,price) {

    cart.push({
        id:id,
        name:name,
        price:price
    });

    localStorage.setItem("cart",JSON.stringify(cart));

    document.getElementById("cartCount").innerText =
        cart.length;

    let message = document.getElementById("message");

    message.innerText = name + " added to cart ✓";
    message.classList.add("show");

    setTimeout(function() {
        message.classList.remove("show");
    },2000);
}

function showCart() {
    window.location.href = "Cart.jsp";
}

function searchProducts() {

    let filter =
        document.getElementById("searchInput")
        .value.toLowerCase();

    let products =
        document.getElementsByClassName("product");

    for(let i=0;i<products.length;i++) {

        let name =
            products[i]
            .getElementsByTagName("h3")[0]
            .innerText.toLowerCase();

        products[i].style.display =
            name.includes(filter) ? "" : "none";
    }
}

</script>

</body>
</html>