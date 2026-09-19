<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>

    <title>Deepa Mart - Login</title>

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        body {
            min-height: 100vh;
            background: #4169e1;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 420px;
            background: white;
            padding: 35px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.2);
        }

        .brand {
            text-align: center;
            color: #4169e1;
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 8px;
        }

        .title {
            text-align: center;
            font-size: 25px;
            margin-bottom: 25px;
            color: #222;
        }

        label {
            display: block;
            margin-bottom: 7px;
            font-size: 14px;
            color: #555;
        }

        input {
            width: 100%;
            padding: 13px;
            margin-bottom: 18px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 15px;
            outline: none;
        }

        input:focus {
            border-color: #4169e1;
        }

        .login-btn {
            width: 100%;
            padding: 13px;
            border: none;
            border-radius: 6px;
            background: #087fce;
            color: white;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }

        .login-btn:hover {
            background: #066bb0;
        }

        .signup-text {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
            color: #555;
        }

        .signup-text a {
            color: #087fce;
            text-decoration: none;
            font-weight: bold;
        }

        .error-message {
            text-align: center;
            color: #c62828;
            font-weight: bold;
            margin-top: 15px;
            font-size: 14px;
        }

    </style>

</head>

<body>

<div class="container">

    <div class="brand">DEEPA MART</div>

    <h2 class="title">Login</h2>

    <form action="${pageContext.request.contextPath}/login" method="post">

        <label>Username</label>

        <input type="text"
               name="username"
               placeholder="Enter username"
               required>

        <label>Password</label>

        <input type="password"
               name="password"
               placeholder="Enter password"
               required>

        <button type="submit" class="login-btn">
            Login
        </button>

    </form>

    <p class="signup-text">
        Don't have an account?
        <a href="${pageContext.request.contextPath}/signup.html">
            Sign Up
        </a>
    </p>


    <!-- Login error message -->

    <% if (request.getAttribute("error") != null) { %>

        <div class="error-message">
            <%= esc(String.valueOf(request.getAttribute("error"))) %>
        </div>

    <% } %>


</div>

</body>
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
</html>