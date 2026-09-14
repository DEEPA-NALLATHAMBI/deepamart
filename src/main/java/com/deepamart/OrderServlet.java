package com.deepamart;
import com.deepamart.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            OrderDAO dao = new OrderDAO();

            List<Order> orders = dao.getAllOrders();

            request.setAttribute("orders", orders);

            request.getRequestDispatcher("my-orders.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            response.getWriter().println("Unable to load orders");
        }
    }
}
