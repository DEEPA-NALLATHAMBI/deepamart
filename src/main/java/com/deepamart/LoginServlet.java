package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";

        response.setContentType("text/html;charset=UTF-8");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                response.getWriter().println(
                    "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<title>Deepa Mart - Welcome</title>" +

                    "<style>" +

                    "* {" +
                    "    box-sizing: border-box;" +
                    "    margin: 0;" +
                    "    padding: 0;" +
                    "    font-family: Arial, sans-serif;" +
                    "}" +

                    "body {" +
                    "    min-height: 100vh;" +
                    "    background: #4169e1;" +
                    "    display: flex;" +
                    "    justify-content: center;" +
                    "    align-items: center;" +
                    "    overflow: hidden;" +
                    "}" +

                    ".card {" +
                    "    width: 430px;" +
                    "    background: white;" +
                    "    padding: 45px 35px;" +
                    "    border-radius: 18px;" +
                    "    text-align: center;" +
                    "    box-shadow: 0 10px 30px rgba(0,0,0,0.25);" +
                    "    position: relative;" +
                    "    z-index: 10;" +
                    "}" +

                    ".logo {" +
                    "    font-size: 30px;" +
                    "    font-weight: bold;" +
                    "    color: #4169e1;" +
                    "    margin-bottom: 20px;" +
                    "}" +

                    ".shopping-icon {" +
                    "    font-size: 65px;" +
                    "    margin-bottom: 15px;" +
                    "}" +

                    "h2 {" +
                    "    color: #222;" +
                    "    font-size: 27px;" +
                    "    margin-bottom: 12px;" +
                    "}" +

                    ".success-message {" +
                    "    color: #555;" +
                    "    font-size: 17px;" +
                    "    margin-bottom: 25px;" +
                    "}" +

                    ".button {" +
                    "    display: inline-block;" +
                    "    padding: 13px 30px;" +
                    "    background: #087fce;" +
                    "    color: white;" +
                    "    text-decoration: none;" +
                    "    border-radius: 7px;" +
                    "    font-weight: bold;" +
                    "}" +

                    ".button:hover {" +
                    "    background: #066bb0;" +
                    "}" +

                    ".confetti {" +
                    "    position: fixed;" +
                    "    width: 10px;" +
                    "    height: 16px;" +
                    "    top: -20px;" +
                    "    animation: fall linear forwards;" +
                    "    z-index: 20;" +
                    "}" +

                    "@keyframes fall {" +
                    "    0% {" +
                    "        transform: translateY(0) rotate(0deg);" +
                    "        opacity: 1;" +
                    "    }" +
                    "    100% {" +
                    "        transform: translateY(110vh) rotate(720deg);" +
                    "        opacity: 0;" +
                    "    }" +
                    "}" +

                    "</style>" +
                    "</head>" +

                    "<body>" +

                    "<div class='card'>" +

                    "<div class='logo'>DEEPA MART</div>" +

                    "<div class='shopping-icon'>🛍️</div>" +

                    "<h2>Welcome Back!</h2>" +

                    "<p class='success-message'>" +
                    "You're Successfully Logged In!" +
                    "</p>" +

                    "<a href='home.html' class='button'>" +
                    "Start Shopping" +
                    "</a>" +

                    "</div>" +

                    "<script>" +

                    "function createConfetti() {" +
                    "    for (let i = 0; i < 80; i++) {" +

                    "        const confetti = document.createElement('div');" +

                    "        confetti.className = 'confetti';" +

                    "        confetti.style.left = Math.random() * 100 + 'vw';" +
                    "        confetti.style.animationDuration = (2 + Math.random() * 3) + 's';" +
                    "        confetti.style.transform = 'rotate(' + Math.random() * 360 + 'deg)';" +

                    "        const colors = ['#ff4757', '#ffa502', '#2ed573', '#1e90ff', '#a55eea'];" +
                    "        confetti.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];" +

                    "        document.body.appendChild(confetti);" +

                    "        setTimeout(() => {" +
                    "            confetti.remove();" +
                    "        }, 5000);" +

                    "    }" +
                    "}" +

                    "createConfetti();" +

                    "</script>" +

                    "</body>" +
                    "</html>"
                );

            } else {

                response.getWriter().println(
                    "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<title>Deepa Mart - Login Error</title>" +
                    "<style>" +
                    "body {" +
                    "    min-height: 100vh;" +
                    "    background: #4169e1;" +
                    "    display: flex;" +
                    "    justify-content: center;" +
                    "    align-items: center;" +
                    "    font-family: Arial, sans-serif;" +
                    "}" +
                    ".card {" +
                    "    background: white;" +
                    "    padding: 40px;" +
                    "    border-radius: 15px;" +
                    "    text-align: center;" +
                    "    box-shadow: 0 8px 25px rgba(0,0,0,0.2);" +
                    "}" +
                    "h2 { color: #e74c3c; }" +
                    "a {" +
                    "    display: inline-block;" +
                    "    margin-top: 20px;" +
                    "    padding: 12px 25px;" +
                    "    background: #087fce;" +
                    "    color: white;" +
                    "    text-decoration: none;" +
                    "    border-radius: 6px;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='card'>" +
                    "<h2>Invalid Username or Password</h2>" +
                    "<p>Please try again.</p>" +
                    "<a href='login.html'>Back to Login</a>" +
                    "</div>" +
                    "</body>" +
                    "</html>"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                "<h2>Login failed</h2>" +
                "<p>Please try again later.</p>"
            );
        }
    }
}