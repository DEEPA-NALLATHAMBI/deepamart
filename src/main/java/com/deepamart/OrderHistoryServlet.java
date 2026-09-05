package com.deepamart;

import com.deepamart.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class OrderHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Object userIdObject = session.getAttribute("userId");

        // User login check
        if (userIdObject == null) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) userIdObject;

        OrderDAO dao = new OrderDAO();

        // Get only this buyer's orders
        List<Order> orders =
                dao.getOrdersByUser(userId);

        request.setAttribute("orders", orders);

        request.getRequestDispatcher("my-orders.jsp")
               .forward(request, response);
    }
}