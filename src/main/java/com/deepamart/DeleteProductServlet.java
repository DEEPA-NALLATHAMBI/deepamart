package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {

    private boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return false;
        }

        String role = (String) session.getAttribute("role");

        return "SELLER".equalsIgnoreCase(role)
                || "ADMIN".equalsIgnoreCase(role);
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAuthorized(request)) {
            response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "Access denied"
            );
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            ProductDAO dao = new ProductDAO();
            dao.deleteProduct(id);

            response.sendRedirect(
                request.getContextPath() + "/products"
            );

        } catch (Exception e) {
            response.getWriter().println(
                "Product deletion failed"
            );
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}