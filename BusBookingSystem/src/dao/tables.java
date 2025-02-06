package dao;
import javax.swing.JOptionPane;

public class tables {
    public static void main(String[] args) {
        try {
            // Create users table
            String userTable = "CREATE TABLE IF NOT EXISTS users ("
                + "userID INT PRIMARY KEY AUTO_INCREMENT, "
                + "username VARCHAR(100) NOT NULL, "
                + "password VARCHAR(255) NOT NULL, "
                + "email VARCHAR(100) UNIQUE NOT NULL, "
                + "userType ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER')";
            DbOperations.setDataOrDelete(userTable, "Users table created successfully");

            // Create buses table
            String busTable = "CREATE TABLE IF NOT EXISTS buses ("
                + "busID VARCHAR(10) PRIMARY KEY, "
                + "source VARCHAR(100) NOT NULL, "
                + "destination VARCHAR(100) NOT NULL, "
                + "departureTime TIMESTAMP NOT NULL, "
                + "arrivalTime TIMESTAMP NOT NULL, "  // Add this line
                + "totalSeats INT NOT NULL, "
                + "ticketPrice DECIMAL(10,2) NOT NULL)";
            DbOperations.setDataOrDelete(busTable, "Buses table created successfully");

            // Create booking table with foreign keys
            String bookingTable = "CREATE TABLE IF NOT EXISTS booking ("
                + "bookingID VARCHAR(50) PRIMARY KEY, "
                + "busID VARCHAR(50) NOT NULL, "
                + "userID INT NOT NULL, "
                + "seatNumber INT NOT NULL, "
                + "customerName VARCHAR(100) NOT NULL, "
                + "ticketPrice DECIMAL(10,2) NOT NULL, "
                + "bookingDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "STATUS ENUM('CONFIRMED', 'CANCELLED', 'PENDING') DEFAULT 'CONFIRMED', "
                + "FOREIGN KEY (userID) REFERENCES users(userID), "
                + "FOREIGN KEY (busID) REFERENCES buses(busID), "
                + "UNIQUE KEY unique_seat_bus (busID, seatNumber))";
            DbOperations.setDataOrDelete(bookingTable, "Booking table created successfully");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}