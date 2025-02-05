/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import javax.swing.JOptionPane;
//import com.mysql.cj.jdbc.Driver;

public class ConnectionProvider{
    private static Connection con;
    public static Connection getCon(){
        try{
            if(con == null){
                //driver class load
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                //create a connection
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/busbooking", 
                        "Insyirah", 
                        "Man1971"
                );
                System.out.println("Database Connected!");
            }
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Connection Failed! Check:\n"
                + "1. MySQL Server is running\n"
                + "2. Database name is correct\n"
                + "3. Credentials are correct",
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
        return con;
    }
}

