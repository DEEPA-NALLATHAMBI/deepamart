package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import com.deepamart.ProductDAO;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            ProductDAO dao = new ProductDAO();
            dao.deleteProduct(id);

            response.sendRedirect("products");

        } catch (Exception e) {
    response.getWriter().println("Product deletion failed");
}
    }
}
