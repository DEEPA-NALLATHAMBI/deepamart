package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/edit-product")
public class EditProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String image = request.getParameter("image");

        try {
            ProductDAO dao = new ProductDAO();

            dao.updateProduct(id, name, category, price, stock, image);

            response.sendRedirect("seller-dashboard.jsp");

        } catch (Exception e) {
            response.getWriter().println("Product update failed");
        }
    }
}
