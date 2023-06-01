package src.db;

import java.sql.Statement;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import src.db.exceptions.DbException;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {

                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                String password = props.getProperty("password");
                String user = props.getProperty("user");

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connected");
                return conn;
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        } else {

            return conn;
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream(".env")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}