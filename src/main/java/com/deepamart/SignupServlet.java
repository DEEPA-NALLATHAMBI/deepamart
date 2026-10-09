package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username == null || email == null || password == null
                || username.isBlank()
                || email.isBlank()
                || password.isBlank()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/signup.html?error=signup");
            return;
        }

        String sql =
                "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try {
            String hashedPassword =
                    BCrypt.hashpw(password, BCrypt.gensalt());

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, hashedPassword);

                ps.executeUpdate();
            }

            response.sendRedirect(
                    request.getContextPath() + "/login");

        } catch (Exception e) {
            getServletContext().log("Signup failed", e);

            response.sendRedirect(
                    request.getContextPath()
                    + "/signup.html?error=signup");
        }
    }
}