package dao;

import java.sql.*;
import javax.swing.JOptionPane;

public class ConnectionProvider {
    private static Connection con;

    public static Connection getCon() {
        try {
            if (con == null || con.isClosed()) {
                // Load driver class
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create a connection
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/busbooking",
                        "root",
                        ""
                );
                System.out.println("Database Connected!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, """
                                                Connection Failed! Check:
                                                1. MySQL Server is running
                                                2. Database name is correct
                                                3. Credentials are correct""",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return con;
    }

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}