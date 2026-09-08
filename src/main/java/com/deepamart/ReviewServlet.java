package com.deepamart;

import com.deepamart.model.Review;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/add-review")
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String productIdParam = request.getParameter("productId");
        String ratingParam = request.getParameter("rating");
        String reviewText = request.getParameter("reviewText");

        if (productIdParam == null || ratingParam == null) {
            response.getWriter().println("Invalid review details.");
            return;
        }

        int productId = Integer.parseInt(productIdParam);
        int rating = Integer.parseInt(ratingParam);

        HttpSession session = request.getSession();

        Object userIdObject = session.getAttribute("userId");

        if (userIdObject == null) {
            response.sendRedirect("login.html");
            return;
        }

        int userId = (int) userIdObject;

        Review review = new Review(
                productId,
                userId,
                rating,
                reviewText
        );

        ReviewDAO dao = new ReviewDAO();
                  if (dao.addReview(review)) {

    response.sendRedirect(
        request.getContextPath()
        + "/products"
    );

} else {
    response.getWriter().println("Review insert failed. Check Tomcat console.");
}
    }
}