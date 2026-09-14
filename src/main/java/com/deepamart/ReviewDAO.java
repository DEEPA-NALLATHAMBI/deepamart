package com.deepamart;

import com.deepamart.model.Review;
import java.sql.*;
import java.util.*;

public class ReviewDAO {

    public boolean addReview(Review review) {

    String sql = "INSERT INTO reviews "
               + "(product_id, user_id, rating, review_text) "
               + "VALUES (?, ?, ?, ?)";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, review.getProductId());
        ps.setInt(2, review.getUserId());
        ps.setInt(3, review.getRating());
        ps.setString(4, review.getReviewText());

        int result = ps.executeUpdate();

        return result > 0;

    } catch (Exception e) {
        return false;
    }
}
    public List<Review> getReviewsByProduct(int productId) {

        List<Review> reviews = new ArrayList<>();

        String sql = "SELECT * FROM reviews " +
                     "WHERE product_id = ? ORDER BY review_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Review review = new Review();

                review.setReviewId(rs.getInt("review_id"));
                review.setProductId(rs.getInt("product_id"));
                review.setUserId(rs.getInt("user_id"));
                review.setRating(rs.getInt("rating"));
                review.setReviewText(rs.getString("review_text"));

                reviews.add(review);
            }

        } catch (Exception e) {
        }

        return reviews;
    }
}
