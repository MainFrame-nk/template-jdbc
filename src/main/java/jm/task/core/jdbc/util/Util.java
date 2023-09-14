package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/test_db";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Database connection successful!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection failed!");
        }

        return connection;
    }

    private static final SessionFactory sessionFactory;
    static {
        try {
            Properties properties = new Properties();
            properties.setProperty("connection.driver_class", DB_DRIVER);
            properties.setProperty("hibernate.connection.url", DB_URL);
            properties.setProperty("hibernate.connection.username", DB_USERNAME);
            properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

            properties.setProperty("hibernate.hbm2ddl.auto", "create");

            sessionFactory = new org.hibernate.cfg.Configuration()
                    .addProperties(properties)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
