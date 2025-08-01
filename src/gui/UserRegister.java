package gui;

import dao.DbOperations;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;


public class UserRegister {
    private JFrame frame;
    // idField, 
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    
    public UserRegister(){
        frame = new JFrame("User Registration");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        userRegister();
        
        frame.setVisible(true);
    }
    
    private void userRegister(){
        JLabel labelName = new JLabel("Username: ");
        labelName.setBounds(30, 30, 150, 25);
        frame.add(labelName);
        
        usernameField = new JTextField();
        usernameField.setBounds(180, 30, 180, 25);
        frame.add(usernameField);
        
        JLabel labelEmail = new JLabel("Email: ");
        labelEmail.setBounds(30, 70, 180, 25);
        frame.add(labelEmail);
        
        emailField = new JTextField();
        emailField.setBounds(180, 70, 180, 25);
        frame.add(emailField);
        
        JLabel labelPassword = new JLabel("Password: ");
        labelPassword.setBounds(30, 110, 150, 25);
        frame.add(labelPassword);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(180, 110, 180, 25);
        frame.add(passwordField);
        
        registerButton = new JButton("Register");
        registerButton.setBounds(140, 160, 120, 30);
        frame.add(registerButton);
        
        registerButton.addActionListener(e -> registerUser());
    }
        
private void registerUser(){
    // String idText = idField.getText();
    String username = usernameField.getText().trim();
    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();
    
    if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
        JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
        try (Connection conn = ConnectionProvider.getCon()) {
            // Check if the email already exists
            String checkQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(frame, "Email already registered!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Insert new user (Auto-increment ID)
            String insertQuery = "INSERT INTO users (username, email, password, userType) VALUES (?, ?, ?, 'USER')";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, email);
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Hash password before storing
            insertStmt.setString(3, hashedPassword);

            int rows = insertStmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new UserLogin(); // Redirect to login
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to register user", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
