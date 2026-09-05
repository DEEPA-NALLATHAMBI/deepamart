package com.deepamart;

import com.deepamart.model.Order;
import com.deepamart.model.OrderItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Object userIdObject = session.getAttribute("userId");

        if (userIdObject == null) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) userIdObject;

        String paymentMethod =
                request.getParameter("paymentMethod");

        String[] productNames =
                request.getParameterValues("productName");

        String[] productPrices =
                request.getParameterValues("productPrice");

        if (productNames == null || productPrices == null) {
            response.sendRedirect("cart.jsp");
            return;
        }

        List<OrderItem> items = new ArrayList<>();

        double total = 0;

        for (int i = 0; i < productNames.length; i++) {

            double price =
                    Double.parseDouble(productPrices[i]);

            OrderItem item =
                    new OrderItem(
                            productNames[i],
                            price,
                            1
                    );

            items.add(item);

            total += price;
        }

        Order order =
                new Order(total, paymentMethod);

        order.setUserId(userId);

        OrderDAO dao = new OrderDAO();

        int orderId =
                dao.saveOrder(order, items);

        if (orderId > 0) {

            session.setAttribute("orderId", orderId);

            response.sendRedirect(
                    request.getContextPath()
                    + "/order-success.jsp"
            );

        } else {

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h2>Order Failed</h2>" +
                    "<p>Please try again.</p>" +
                    "<a href='checkout.jsp'>Back to Checkout</a>"
            );
        }
    }
}
