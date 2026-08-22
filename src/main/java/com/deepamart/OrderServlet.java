package com.deepamart;

import com.deepamart.model.Order;
import com.deepamart.model.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/place-order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String paymentMethod = request.getParameter("paymentMethod");
        String[] productNames = request.getParameterValues("productName");
        String[] productPrices = request.getParameterValues("productPrice");

        if (productNames == null || productPrices == null ||
                productNames.length == 0) {

            response.sendRedirect("cart.jsp");
            return;
        }

        List<OrderItem> items = new ArrayList<>();
        double totalAmount = 0;

        for (int i = 0; i < productNames.length; i++) {

            double price = Double.parseDouble(productPrices[i]);

            OrderItem item = new OrderItem(
                    productNames[i],
                    price,
                    1
            );

            items.add(item);
            totalAmount += price;
        }

        Order order = new Order(
                totalAmount,
                paymentMethod
        );

        OrderDAO orderDAO = new OrderDAO();

        int orderId = orderDAO.saveOrder(order, items);

        if (orderId > 0) {

            response.sendRedirect(
                    "order-success.jsp?orderId=" + orderId
            );

        } else {

            response.getWriter().println(
                    "Order could not be placed. Please try again."
            );
        }
    }
}
