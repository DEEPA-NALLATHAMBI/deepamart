package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/edit-product")
public class EditProductServlet extends HttpServlet {

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

        String id = request.getParameter("id");

        if (id == null) {
            response.sendRedirect("seller-dashboard.jsp");
            return;
        }

        request.getRequestDispatcher("edit-product.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
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
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            double price = Double.parseDouble(
                request.getParameter("price")
            );
            int stock = Integer.parseInt(
                request.getParameter("stock")
            );
            String image = request.getParameter("image");

            ProductDAO dao = new ProductDAO();

            dao.updateProduct(
                id, name, category, price, stock, image
            );

            response.sendRedirect("seller-dashboard.jsp");

        } catch (Exception e) {
            response.getWriter().println(
                "Product update failed"
            );
        }
    }
}