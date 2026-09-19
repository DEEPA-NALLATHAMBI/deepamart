package com.deepamart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static final Properties properties = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new RuntimeException(
                    "Database driver loading failed", e);
        }
    }

    public static Connection getConnection() throws Exception {

        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        if (host == null || port == null || database == null
                || user == null || password == null) {

            throw new RuntimeException(
                    "Railway MySQL environment variables missing");
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/"
                + database
                + "?useSSL=false&allowPublicKeyRetrieval=true"
                + "&serverTimezone=UTC";

        return DriverManager.getConnection(
                url,
                user,
                password
        );
    }
}