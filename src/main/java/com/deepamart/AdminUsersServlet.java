package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/admin-users")
public class AdminUsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> users = new ArrayList<>();

        String sql = "SELECT id, name, role FROM users ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();

                user.put("id", rs.getInt("id"));
                user.put("name", rs.getString("name"));
                user.put("role", rs.getString("role"));

                users.add(user);
            }

            request.setAttribute("users", users);
            request.getRequestDispatcher("admin-users.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Unable to load users.");
        }
    }
}
