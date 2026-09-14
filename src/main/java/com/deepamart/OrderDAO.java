package com.deepamart;

import com.deepamart.model.Order;
import com.deepamart.model.OrderItem;
import java.sql.*;
import java.util.*;

public class OrderDAO {

    public int saveOrder(Order order, List<OrderItem> items) {

        int orderId = 0;

        String orderSql =
            "INSERT INTO orders " +
            "(total_amount, payment_method, order_status, user_id) " +
            "VALUES (?, ?, ?, ?)";

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
                        Statement.RETURN_GENERATED_KEYS
                    );
                PreparedStatement itemStmt =
                    con.prepareStatement(itemSql)
            ) {

                orderStmt.setDouble(1, order.getTotalAmount());
                orderStmt.setString(2, order.getPaymentMethod());
                orderStmt.setString(3, order.getOrderStatus());
                orderStmt.setInt(4, order.getUserId());

                orderStmt.executeUpdate();

                ResultSet rs = orderStmt.getGeneratedKeys();

                if (rs.next()) {
                    orderId = rs.getInt(1);
                }

                for (OrderItem item : items) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setString(2, item.getProductName());
                    itemStmt.setDouble(3, item.getProductPrice());
                    itemStmt.setInt(4, item.getQuantity());
                    itemStmt.addBatch();
                }

                itemStmt.executeBatch();
                con.commit();

            } catch (Exception e) {
                con.rollback();
                return 0;
            }

        } catch (Exception e) {
            return 0;
        }

        return orderId;
    }

    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        String sql =
            "SELECT * FROM orders ORDER BY order_id DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Order order = new Order();

                order.setOrderId(rs.getInt("order_id"));
                order.setTotalAmount(
                    rs.getDouble("total_amount")
                );
                order.setPaymentMethod(
                    rs.getString("payment_method")
                );
                order.setOrderStatus(
                    rs.getString("order_status")
                );

                orders.add(order);
            }

        } catch (Exception e) {
        }

        return orders;
    }

    public List<Order> getOrdersByUser(int userId) {

        List<Order> orders = new ArrayList<>();

        String sql =
            "SELECT * FROM orders WHERE user_id=? " +
            "ORDER BY order_id DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Order order = new Order();

                order.setOrderId(
                    rs.getInt("order_id")
                );

                order.setUserId(
                    rs.getInt("user_id")
                );

                order.setTotalAmount(
                    rs.getDouble("total_amount")
                );

                order.setPaymentMethod(
                    rs.getString("payment_method")
                );

                order.setOrderStatus(
                    rs.getString("order_status")
                );

                orders.add(order);
            }

        } catch (Exception e) {
        }

        return orders;
    }

    public boolean updateOrderStatus(
            int orderId, String status) {

        String sql =
            "UPDATE orders SET order_status=? " +
            "WHERE order_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public List<OrderItem> getOrderItems(int orderId) {

        List<OrderItem> items = new ArrayList<>();

        String sql =
            "SELECT * FROM order_items WHERE order_id=?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                con.prepareStatement(sql)
        ) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                OrderItem item = new OrderItem();

                item.setOrderItemId(
                    rs.getInt("order_item_id")
                );

                item.setOrderId(
                    rs.getInt("order_id")
                );

                item.setProductName(
                    rs.getString("product_name")
                );

                item.setProductPrice(
                    rs.getDouble("product_price")
                );

                item.setQuantity(
                    rs.getInt("quantity")
                );

                items.add(item);
            }

        } catch (Exception e) {
        }

        return items;
    }
}