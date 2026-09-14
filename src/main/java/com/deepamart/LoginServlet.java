package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Open login page
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp")
               .forward(request, response);
    }

    // Login process
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Basic validation
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {

            showError(request, response,
                    "Username and Password are required.");
            return;
        }

        String sql = "SELECT * FROM users WHERE name = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username.trim());

            try (ResultSet rs = ps.executeQuery()) {

                // Username not found
                if (!rs.next()) {

                    showError(request, response,
                            "Invalid Username or Password");
                    return;
                }

                int userId = rs.getInt("id");
                String dbUsername = rs.getString("name");
                String storedPassword = rs.getString("password");
                String role = rs.getString("role");

                boolean passwordCorrect = false;

                /*
                 * Check whether password is already BCrypt.
                 */
                if (storedPassword != null &&
                        (storedPassword.startsWith("$2a$")
                        || storedPassword.startsWith("$2b$")
                        || storedPassword.startsWith("$2y$"))) {

                    try {
                        passwordCorrect =
                                BCrypt.checkpw(password, storedPassword);
                    } catch (IllegalArgumentException e) {
                        passwordCorrect = false;
                    }

                } else {

                    /*
                     * Existing accounts may still have
                     * plain-text passwords.
                     *
                     * Allow them to login once and then
                     * convert the password to BCrypt.
                     */
                    passwordCorrect =
                            password.equals(storedPassword);
                }

                // Wrong password
                if (!passwordCorrect) {

                    showError(request, response,
                            "Invalid Username or Password");
                    return;
                }

                /*
                 * If old password was plain text,
                 * convert it to BCrypt after successful login.
                 */
                if (storedPassword != null &&
                        !storedPassword.startsWith("$2a$")
                        && !storedPassword.startsWith("$2b$")
                        && !storedPassword.startsWith("$2y$")) {

                    String hashedPassword =
                            BCrypt.hashpw(password, BCrypt.gensalt());

                    String updateSql =
                            "UPDATE users SET password = ? WHERE id = ?";

                    try (PreparedStatement updatePs =
                                 con.prepareStatement(updateSql)) {

                        updatePs.setString(1, hashedPassword);
                        updatePs.setInt(2, userId);
                        updatePs.executeUpdate();
                    }
                }

                /*
                 * Prevent session fixation:
                 * invalidate old session and create a new one.
                 */
                HttpSession oldSession =
                        request.getSession(false);

                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession session =
                        request.getSession(true);

                // 30 minutes session timeout
                session.setMaxInactiveInterval(30 * 60);

                // Store login information
                session.setAttribute("userId", userId);
                session.setAttribute("username", dbUsername);
                session.setAttribute("role", role);

                /*
                 * Role-based navigation
                 */
                if ("ADMIN".equalsIgnoreCase(role)) {

                    response.sendRedirect(
                            request.getContextPath()
                            + "/admin-dashboard.jsp");

                } else if ("SELLER".equalsIgnoreCase(role)) {

                    response.sendRedirect(
                            request.getContextPath()
                            + "/seller-dashboard.jsp");

                } else {

                    // Buyer
                    response.sendRedirect(
                            request.getContextPath()
                            + "/login-success.jsp");
                }
            }

       } catch (Exception e) {
    showError(request, response,
            "Login failed. Please try again.");
}
    }

    /*
     * Display error inside login.jsp
     * without changing the URL.
     */
    private void showError(HttpServletRequest request,
                           HttpServletResponse response,
                           String message)
            throws ServletException, IOException {

        request.setAttribute("error", message);

        request.getRequestDispatcher("/login.jsp")
               .forward(request, response);
    }
}