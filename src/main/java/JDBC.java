import ViewWindow.MainViewWindow;

import java.sql.*;


public class JDBC {
    static final String DATABASE_URL = "jdbc:mysql://localhost/cinema?useSSL=false&characterEncoding=Cp1251";

    static final String USER = "root";
    static final String PASSWORD = "password";

    public static void main(String[] args) throws SQLException {
        connect();
    }

    private static void connect() throws SQLException {
        Connection connection;
        Statement statement;
        try {
            System.out.println("Registering JDBC driver...");
            System.out.println("Creating connection to database...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            System.out.println("Getting records...");
            statement = connection.createStatement();
            MainViewWindow window = new MainViewWindow(statement, connection);
            window.frame.setVisible(true);
        } finally {
           System.out.println("successful job");
        }
    }


}