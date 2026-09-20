package com.deepamart;

import com.deepamart.model.Review;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({"/review", "/add-review"})
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        request.getRequestDispatcher("/review.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int productId = Integer.parseInt(
                    request.getParameter("productId"));

            int rating = Integer.parseInt(
                    request.getParameter("rating"));

            String reviewText = request.getParameter("reviewText");

            Object userIdObject = session.getAttribute("userId");
            int userId = Integer.parseInt(
                    userIdObject.toString());

            if (rating < 1 || rating > 5 ||
                reviewText == null ||
                reviewText.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/review.jsp?productId=" + productId);

                return;
            }

            Review review = new Review(
                    productId,
                    userId,
                    rating,
                    reviewText.trim()
            );

            ReviewDAO dao = new ReviewDAO();

            if (dao.addReview(review)) {

                response.sendRedirect(
                        request.getContextPath() + "/products");

            } else {
                response.getWriter().println(
                        "Review insert failed.");
            }

        } catch (Exception e) {

            response.getWriter().println(
                    "Error while submitting review: "
                    + e.getMessage());
        }
    }
}