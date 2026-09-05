package com.deepamart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
           
   
@WebServlet("/update-order-status")
public class UpdateOrderStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(
            request.getParameter("orderId")
        );

        String status = request.getParameter("status");

        OrderDAO dao = new OrderDAO();

        dao.updateOrderStatus(orderId, status);

        response.sendRedirect(
            request.getContextPath() + "/seller-orders"
        );
    }
}