package com.deepamart;

import com.deepamart.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class OrderHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        OrderDAO dao = new OrderDAO();
        List<Order> orders = dao.getAllOrders();

        request.setAttribute("orders", orders);

        request.getRequestDispatcher("my-orders.jsp")
               .forward(request, response);
    }
}