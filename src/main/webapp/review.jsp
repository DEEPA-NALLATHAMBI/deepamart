<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rate & Review - Deepa Mart</title>

<style>
body{
    margin:0;
    font-family:Arial;
    background:#f5f5f5;
}

.header{
    background:#232f3e;
    color:white;
    padding:20px;
    text-align:center;
    font-size:28px;
    font-weight:bold;
}

.container{
    width:450px;
    max-width:90%;
    margin:50px auto;
    background:white;
    padding:30px;
    border-radius:12px;
    box-shadow:0 2px 10px rgba(0,0,0,.15);
}

h2{
    text-align:center;
    margin-bottom:25px;
}

label{
    display:block;
    margin-top:15px;
    margin-bottom:8px;
    font-weight:bold;
}

select,
textarea{
    width:100%;
    padding:12px;
    border:1px solid #ccc;
    border-radius:8px;
    font-size:15px;
}

textarea{
    height:120px;
    resize:none;
}

.submit-btn{
    width:100%;
    margin-top:20px;
    padding:12px;
    border:none;
    border-radius:20px;
    background:#ffd814;
    font-weight:bold;
    cursor:pointer;
}

.back-btn{
    display:block;
    text-align:center;
    margin-top:15px;
    text-decoration:none;
    color:#232f3e;
    font-weight:bold;
}
</style>
</head>

<body>

<div class="header">
    🛒 Deepa Mart
</div>

<div class="container">

    <h2>⭐ Rate & Review</h2>

    <form action="<%=request.getContextPath()%>/add-review" method="post">

        <input type="hidden"
               name="productId"
               value="<%=request.getParameter("productId")%>">

        <label>Rating</label>

        <select name="rating" required>
            <option value="">Select Rating</option>
            <option value="5">⭐⭐⭐⭐⭐ 5 - Excellent</option>
            <option value="4">⭐⭐⭐⭐ 4 - Very Good</option>
            <option value="3">⭐⭐⭐ 3 - Good</option>
            <option value="2">⭐⭐ 2 - Average</option>
            <option value="1">⭐ 1 - Poor</option>
        </select>

        <label>Your Review</label>

        <textarea name="reviewText"
                  maxlength="500"
                  placeholder="Write your review..."
                  required></textarea>

        <button type="submit" class="submit-btn">
            Submit Review
        </button>

    </form>

    <a href="products" class="back-btn">
        ← Back to Products
    </a>

</div>

</body>
</html>