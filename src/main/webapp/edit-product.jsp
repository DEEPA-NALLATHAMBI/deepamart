<%@ page import="com.deepamart.Product" %>
<%@ page import="com.deepamart.ProductDAO" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    Product product = new ProductDAO().getProductById(id);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Product - Deepa Mart</title>
    <style>
        body {
            font-family: Arial;
            background: #f5f5f5;
            text-align: center;
        }

        .box {
            background: white;
            width: 400px;
            margin: 50px auto;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 2px 10px #ccc;
        }

        input {
            width: 90%;
            padding: 10px;
            margin: 8px;
        }

        button {
            padding: 12px 25px;
            background: #ffd814;
            border: none;
            border-radius: 20px;
            font-weight: bold;
            cursor: pointer;
        }
    </style>
</head>

<body>

<div class="box">
    <h1>Edit Product</h1>

    <form action="edit-product" method="post">

        <input type="hidden" name="id"
               value="<%=product.getProductId()%>">

        <input type="text" name="name"
               value="<%=product.getProductName()%>"
               required>

        <input type="text" name="category"
               value="<%=product.getCategory()%>"
               required>

        <input type="number" name="price"
               value="<%=product.getPrice()%>"
               step="0.01" required>

        <input type="number" name="stock"
               value="<%=product.getStock()%>"
               required>

        <input type="text" name="image"
               value="<%=product.getImageUrl()%>">

        <br>

        <button type="submit">Update Product</button>
    </form>
</div>

</body>
</html>