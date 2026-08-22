package com.deepamart;

import com.deepamart.model.Order;
import com.deepamart.model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class OrderDAO {

    public int saveOrder(Order order, List<OrderItem> items) {

        int orderId = 0;

        String orderSql =
                "INSERT INTO orders " +
                "(total_amount, payment_method, order_status) " +
                "VALUES (?, ?, ?)";

        String itemSql =
                "INSERT INTO order_items " +
                "(order_id, product_name, product_price, quantity) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (
                PreparedStatement orderStmt =
                    con.prepareStatement(
                        orderSql,
                        PreparedStatement.RETURN_GENERATED_KEYS
                    );
                PreparedStatement itemStmt =
                    con.prepareStatement(itemSql)
            ) {

                orderStmt.setDouble(1, order.getTotalAmount());
                orderStmt.setString(2, order.getPaymentMethod());
                orderStmt.setString(3, order.getOrderStatus());

                orderStmt.executeUpdate();

                ResultSet rs = orderStmt.getGeneratedKeys();

                if (rs.next()) {
                    orderId = rs.getInt(1);
                }

                for (OrderItem item : items) {

                    itemStmt.setInt(1, orderId);
                    itemStmt.setString(
                        2,
                        item.getProductName()
                    );
                    itemStmt.setDouble(
                        3,
                        item.getProductPrice()
                    );
                    itemStmt.setInt(
                        4,
                        item.getQuantity()
                    );

                    itemStmt.addBatch();
                }

                itemStmt.executeBatch();

                con.commit();

            } catch (Exception e) {

                con.rollback();
                e.printStackTrace();
                return 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return orderId;
    }
}
