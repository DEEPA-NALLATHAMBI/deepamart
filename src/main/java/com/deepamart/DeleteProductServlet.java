package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {

    // Check Seller and Admin authorization
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

    // Delete product using GET
    @Override
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

        String idParameter = request.getParameter("id");

        if (idParameter == null || idParameter.trim().isEmpty()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Product ID is missing"
            );

            return;
        }

        try {

    int id = Integer.parseInt(idParameter);

    ProductDAO dao = new ProductDAO();

    dao.deleteProduct(id);

    response.sendRedirect(
            request.getContextPath() + "/products"
    );

} catch (NumberFormatException e) {

    response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Invalid product ID"
    );

} catch (Exception e) {

    response.sendError(
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            "Product deletion failed"
    );
}
    }

    // Support POST requests also
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}