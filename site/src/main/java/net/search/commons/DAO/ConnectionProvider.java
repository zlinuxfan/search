package net.search.commons.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    static Connection connection = null;
    static String userName = "search_main_user";
    static String password = "500";
    static String urlConnection = "jdbc:postgresql://194.87.219.8:5432/postgres";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(urlConnection, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
