<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.model.Product" %>
<%@ page import="com.deepamart.model.Review" %>
<%@ page import="com.deepamart.ReviewDAO" %>

<%!
    public String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    String role = (String) session.getAttribute("role");

    if (products == null) {
        products = new java.util.ArrayList<>();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Products - Deepa Mart</title>

    <style>
        * { box-sizing:border-box; }

        body {
            margin:0;
            font-family:Arial,sans-serif;
            background:#f5f5f5;
        }

        .header {
            background:#222;
            color:white;
            padding:15px 30px;
            display:flex;
            justify-content:space-between;
            align-items:center;
        }

        .header h2 { margin:0; }

        .nav a {
            color:white;
            text-decoration:none;
            margin-left:20px;
        }

        .search {
            text-align:center;
            padding:20px;
            background:white;
        }

        .search input {
            width:60%;
            max-width:500px;
            padding:12px;
            border:1px solid #ccc;
            border-radius:6px;
        }

        .message {
            position:fixed;
            top:80px;
            right:20px;
            background:#222;
            color:white;
            padding:12px 18px;
            border-radius:6px;
            display:none;
            z-index:10;
        }

        .message.show { display:block; }

        .products {
            display:grid;
            grid-template-columns:repeat(auto-fit,minmax(220px,1fr));
            gap:20px;
            padding:25px;
        }

        .card {
            background:white;
            padding:15px;
            border-radius:10px;
            box-shadow:0 2px 8px #ccc;
        }

        .card img {
            width:100%;
            height:180px;
            object-fit:contain;
            border-radius:8px;
        }

        .card h3 { margin:10px 0 5px; }

        .category {
            color:#666;
            font-size:14px;
        }

        .price {
            font-size:18px;
            font-weight:bold;
            margin:10px 0;
        }

        button, .btn {
            border:0;
            padding:9px 12px;
            border-radius:5px;
            cursor:pointer;
            text-decoration:none;
            display:inline-block;
            margin:3px;
        }

        .cart-btn { background:#222;color:white; }
        .edit { background:#ffc107;color:#000; }
        .delete { background:#dc3545;color:white; }
        .review { background:#198754;color:white; }

        .reviews {
            margin-top:12px;
            padding-top:10px;
            border-top:1px solid #ddd;
        }

        .review-item {
            font-size:13px;
            margin:7px 0;
        }

        .stars { color:#f5a623; }

        footer {
            text-align:center;
            padding:20px;
            background:#222;
            color:white;
        }
    </style>
</head>

<body>

<div class="header">
    <h2>🛒 Deepa Mart</h2>

    <div class="nav">
        <a href="<%=request.getContextPath()%>/products">Products</a>

        <% if ("BUYER".equalsIgnoreCase(role)) { %>
            <a href="<%=request.getContextPath()%>/my-orders">My Orders</a>
            <a href="<%=request.getContextPath()%>/cart">
                Cart (<span id="cartCount">0</span>)
            </a>
        <% } %>

        <% if ("SELLER".equalsIgnoreCase(role)) { %>
            <a href="<%=request.getContextPath()%>/seller-dashboard.jsp">Dashboard</a>
        <% } %>

        <% if ("ADMIN".equalsIgnoreCase(role)) { %>
            <a href="<%=request.getContextPath()%>/admin-dashboard.jsp">Dashboard</a>
        <% } %>
    </div>
</div>

<div id="message" class="message"></div>

<div class="search">
    <input type="text"
           id="searchBox"
           placeholder="Search products..."
           onkeyup="searchProducts()">
</div>

<div class="products" id="productList">

<%
    for (Product product : products) {

        String productName = esc(product.getProductName());
        String category = esc(product.getCategory());

        String imagePath = product.getImageUrl();

        if (imagePath == null || imagePath.trim().isEmpty()) {
            imagePath = "images/default.jpg";
        } else if (!imagePath.startsWith("images/")) {
            imagePath = "images/" + imagePath;
        }

        imagePath = esc(imagePath);

        int productId = product.getProductId();
        double price = product.getPrice();
%>

    <div class="card product-card"
         data-name="<%=productName%>"
         data-category="<%=category%>">

        <img src="<%=request.getContextPath()%>/<%=imagePath%>"
             alt="<%=productName%>"
             onerror="this.src='<%=request.getContextPath()%>/images/default.jpg'">

        <h3><%=productName%></h3>

        <div class="category">
            Category: <%=category%>
        </div>

        <div class="price">
            ₹<%=price%>
        </div>

        <% if ("BUYER".equalsIgnoreCase(role)) { %>

            <button class="cart-btn add-cart"
                    type="button"
                    data-id="<%=productId%>"
                    data-name="<%=productName%>"
                    data-price="<%=price%>"
                    onclick="addToCart(this)">
                Add to Cart
            </button>

            <a class="btn review"
               href="<%=request.getContextPath()%>/review?productId=<%=productId%>">
                Review
            </a>

        <% } %>

        <% if ("SELLER".equalsIgnoreCase(role)
                || "ADMIN".equalsIgnoreCase(role)) { %>

            <a class="btn edit"
               href="<%=request.getContextPath()%>/edit-product?id=<%=productId%>">
                Edit
            </a>

            <a class="btn delete"
               href="<%=request.getContextPath()%>/delete-product?id=<%=productId%>"
               onclick="return confirm('Delete this product?');">
                Delete
            </a>

        <% } %>

        <div class="reviews">
            <b>Reviews</b>

<%
            try {
                ReviewDAO reviewDAO = new ReviewDAO();
                List<Review> reviews =
                    reviewDAO.getReviewsByProduct(productId);

                if (reviews != null && !reviews.isEmpty()) {

                    for (Review review : reviews) {
%>

                    <div class="review-item">
                        <span class="stars">
                            <%=review.getRating()%> ★
                        </span>
                        -
                        <%=esc(review.getComment())%>
                    </div>

<%
                    }

                } else {
%>

                    <div class="review-item">
                        No reviews yet.
                    </div>

<%
                }

            } catch (Exception e) {
%>

                <div class="review-item">
                    Reviews unavailable.
                </div>

<%
            }
%>

        </div>
    </div>

<%
    }
%>

</div>

<footer>
    © 2026 Deepa Mart
</footer>

<script>
    let cart = JSON.parse(localStorage.getItem("cart")) || [];

    function updateCartCount() {
        document.getElementById("cartCount").innerText = cart.length;
    }

    function addToCart(button) {
        let id = Number(button.dataset.id);
        let name = button.dataset.name;
        let price = Number(button.dataset.price);

        cart.push({
            id: id,
            name: name,
            price: price
        });

        localStorage.setItem("cart", JSON.stringify(cart));
        updateCartCount();

        let message = document.getElementById("message");
        message.innerText = name + " added to cart ✓";
        message.classList.add("show");

        setTimeout(function() {
            message.classList.remove("show");
        }, 2000);
    }

    function searchProducts() {
        let text =
            document.getElementById("searchBox")
                    .value.toLowerCase();

        let cards =
            document.querySelectorAll(".product-card");

        cards.forEach(function(card) {
            let name = card.dataset.name.toLowerCase();
            let category = card.dataset.category.toLowerCase();

            if (name.includes(text) || category.includes(text)) {
                card.style.display = "";
            } else {
                card.style.display = "none";
            }
        });
    }

    updateCartCount();
</script>

</body>
</html>