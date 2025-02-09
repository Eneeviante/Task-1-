package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/test_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1111";

    private static Connection conn;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            if(conn != null && !conn.isClosed()) return conn;
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory != null) { return sessionFactory; }

        try{
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USER)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.hbm2ddl.auto", "create-drop");

            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return sessionFactory;
    }
}
