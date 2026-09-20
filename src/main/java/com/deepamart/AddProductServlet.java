package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String image = request.getParameter("image");

        try {
            ProductDAO dao = new ProductDAO();

            dao.addProduct(name, category, price, stock, image);

            response.sendRedirect("products");

        } catch (Exception e) {

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(
                "<h2>Product adding failed</h2>" +
                "<p>Please try again.</p>"
            );
        }
    }
}
