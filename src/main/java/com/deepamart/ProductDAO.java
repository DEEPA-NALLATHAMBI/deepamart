package com.deepamart;

import java.sql.*;
import java.util.*;

public class ProductDAO {

    public List<Product> getAllProducts() throws Exception {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT product_id, product_name, category, price, stock, image_url FROM products";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getString("image_url")
                ));
            }
        }
        return list;
    }

    public void addProduct(String name, String category,
                           double price, int stock, String image)
                           throws Exception {

        String sql = "INSERT INTO products(product_name,category,price,stock,image_url) VALUES(?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setInt(4, stock);
            ps.setString(5, image);
            ps.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws Exception {

        String sql = "DELETE FROM products WHERE product_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}