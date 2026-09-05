package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String sql = "SELECT * FROM users WHERE name=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String role = rs.getString("role");

                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);
                session.setAttribute("userId", rs.getInt("id"));

                if ("SELLER".equalsIgnoreCase(role)) {
                    response.sendRedirect(
                        request.getContextPath() +
                        "/seller-dashboard.jsp"
                    );
                } else {
                    response.sendRedirect(
                        request.getContextPath() +
                        "/login-success.jsp"
                    );
                }

            } else {

                response.setContentType("text/html;charset=UTF-8");

                response.getWriter().println(
                    "<h2>Invalid Username or Password</h2>" +
                    "<a href='login.html'>Back to Login</a>"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                "<h2>Login Failed</h2>" +
                "<p>Please try again later.</p>"
            );
        }
    }
}