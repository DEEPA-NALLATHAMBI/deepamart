package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws
    ServletException{
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> products;
        try{
         products= productDAO.getAllProducts();
         System.out.println("Products found: " + products.size());
        } catch(Exception e){
            e.printStackTrace();
            throw new ServletException(e);
        }

        request.setAttribute("products", products);

        request.getRequestDispatcher("products.jsp")
               .forward(request, response);
    }
}
