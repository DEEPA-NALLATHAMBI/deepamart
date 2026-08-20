<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.deepamart.Product" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Deepa Mart</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f5f5f5;
        }

        /* Header */
        .header {
            background: #232f3e;
            color: white;
            padding: 15px 30px;
            display: flex;
            align-items: center;
            gap: 25px;
        }

        .logo {
            font-size: 28px;
            font-weight: bold;
            white-space: nowrap;
        }

        .search-box {
            flex: 1;
            display: flex;
        }

        .search-box input {
            width: 100%;
            padding: 12px;
            border: none;
            outline: none;
            font-size: 16px;
        }

        .search-box button {
            padding: 12px 20px;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        .cart {
            font-size: 18px;
            cursor: pointer;
            white-space: nowrap;
        }

        /* Main */
        .main {
            padding: 25px 40px;
        }

        .title {
            margin-bottom: 25px;
            font-size: 28px;
        }

        /* Products */
        .products {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 22px;
        }

        .product {
            background: white;
            border-radius: 12px;
            padding: 18px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.12);
            transition: transform 0.2s;
        }

        .product:hover {
            transform: translateY(-5px);
        }

        .product img {
            width: 100%;
            height: 180px;
            object-fit: contain;
            margin-bottom: 12px;
        }

        .product h3 {
            margin: 8px 0;
            font-size: 20px;
        }

        .category {
            color: #666;
            margin: 5px 0;
        }

        .price {
            font-size: 22px;
            font-weight: bold;
            margin: 10px 0;
        }

        .stock {
            color: green;
            margin-bottom: 15px;
        }

        .add-cart {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 20px;
            background: #ffd814;
            cursor: pointer;
            font-size: 15px;
            font-weight: bold;
        }

        .add-cart:hover {
            background: #f7ca00;
        }

        /* Footer */
        footer {
            margin-top: 40px;
            padding: 20px;
            text-align: center;
            background: #232f3e;
            color: white;
        }
    </style>
</head>

<body>

<div class="header">

    <div class="logo">🛒 Deepa Mart</div>

    <div class="search-box">
        <input type="text" id="searchInput"
               placeholder="Search products...">
        <button onclick="searchProducts()">Search</button>
    </div>

    <div class="cart">
        🛒 Cart (<span id="cartCount">0</span>)
    </div>

</div>

<div class="main">

    <h1 class="title">Shop Products</h1>

    <div class="products" id="productsContainer">

    <%
        List<Product> products =
            (List<Product>) request.getAttribute("products");

        if (products != null) {
            for (Product product : products) {
    %>

        <div class="product">

            <img src="/deepa-mart/<%= product.getImageUrl() %>"
                 alt="<%= product.getProductName() %>">

            <h3><%= product.getProductName() %></h3>

            <p class="category">
                <%= product.getCategory() %>
            </p>

            <p class="price">
                ₹<%= product.getPrice() %>
            </p>

            <p class="stock">
                In Stock: <%= product.getStock() %>
            </p>

            <button class="add-cart"
                    onclick="addToCart()">
                Add to Cart
            </button>

        </div>

    <%
            }
        }
    %>

    </div>

</div>

<footer>
    © 2026 Deepa Mart | Happy Shopping 🛒
</footer>

<script>
    let cartCount = 0;

    function addToCart() {
        cartCount++;
        document.getElementById("cartCount").innerText = cartCount;
        alert("Product added to cart!");
    }

    function searchProducts() {
        let input = document.getElementById("searchInput");
        let filter = input.value.toLowerCase();

        let products = document.getElementsByClassName("product");

        for (let i = 0; i < products.length; i++) {

            let name = products[i]
                .getElementsByTagName("h3")[0]
                .innerText
                .toLowerCase();

            if (name.includes(filter)) {
                products[i].style.display = "";
            } else {
                products[i].style.display = "none";
            }
        }
    }
</script>

</body>
</html>