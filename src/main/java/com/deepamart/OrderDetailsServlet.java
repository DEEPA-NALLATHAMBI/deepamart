package com.deepamart;

import com.deepamart.model.OrderItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-details")
public class OrderDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdParam =
                request.getParameter("orderId");

        if (orderIdParam == null) {
            response.sendRedirect("my-orders");
            return;
        }

        int orderId =
                Integer.parseInt(orderIdParam);

        OrderDAO dao = new OrderDAO();

        List<OrderItem> items =
                dao.getOrderItems(orderId);

        request.setAttribute("items", items);
        request.setAttribute("orderId", orderId);

        request.getRequestDispatcher("order-details.jsp")
               .forward(request, response);
    }
}
