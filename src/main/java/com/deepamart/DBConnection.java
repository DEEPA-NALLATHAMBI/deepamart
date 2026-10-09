package com.deepamart;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "MySQL driver loading failed", e
            );
        }
    }

    public static Connection getConnection() throws Exception {

        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        // Railway environment variables irundhaal Railway connection
        if (host != null && port != null
                && database != null && user != null
                && password != null) {

            String url = "jdbc:mysql://" + host + ":" + port
                    + "/" + database
                    + "?serverTimezone=UTC";

            return DriverManager.getConnection(
                    url, user, password
            );
        }

        // Local MySQL connection
        String url = "jdbc:mysql://localhost:3306/deepa_mart"
                + "?serverTimezone=UTC";

        String localUser = "root";
        String localPassword = "deepa123@";

        return DriverManager.getConnection(
                url, localUser, localPassword
        );
    }
}