<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Product - Deepa Mart</title>
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

    <h1>Add Product</h1>

    <form action="add-product" method="post">

        <input type="text" name="name"
               placeholder="Product Name" required>

        <input type="text" name="category"
               placeholder="Category" required>

        <input type="number" name="price"
               placeholder="Price" step="0.01" required>

        <input type="number" name="stock"
               placeholder="Stock" required>

        <input type="text" name="image"
               placeholder="Image File Name">

        <br>

        <button type="submit">Add Product</button>

    </form>

</div>

</body>
</html>