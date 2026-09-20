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
    Object roleObject = session.getAttribute("role");
    if (roleObject == null) {
        return false;
    }
    String role = roleObject.toString().trim();
    return role.equalsIgnoreCase("SELLER")
            || role.equalsIgnoreCase("ADMIN");
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
            response.sendRedirect(request.getContextPath()+"/products");
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

            response.sendRedirect(request.getContextPath()+"/products");

        } catch (Exception e) {
            response.getWriter().println(
                "Product update failed"
            );
        }
    }
}