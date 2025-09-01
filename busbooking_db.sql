-- Database creation script for Bus Booking System

CREATE TABLE users (
  userID INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100),
  userType ENUM('ADMIN','USER')
);

CREATE TABLE buses (
  busID VARCHAR(10) PRIMARY KEY,
  source VARCHAR(100),
  destination VARCHAR(100),
  departureDate DATE,
  departureTime TIMESTAMP,
  totalSeats INT,
  ticketPrice DECIMAL(10,2)
);

CREATE TABLE booking (
  bookingID VARCHAR(50) PRIMARY KEY,
  busID VARCHAR(50),
  userID INT,
  seatNumber INT,
  customerName VARCHAR(100),
  ticketPrice DECIMAL(10,2),
  bookingDateTime TIMESTAMP,
  STATUS ENUM('CONFIRMED','CANCELLED','PENDING'),
  FOREIGN KEY (userID) REFERENCES users(userID),
  FOREIGN KEY (busID) REFERENCES buses(busID)
);

-- Sample data for Bus Booking System

INSERT INTO users (userID, username, password, email, userType) VALUES
(1, 'admin1', 'adminpass1', 'admin1@email.com', 'ADMIN'),
(2, 'admin2', 'adminpass2', 'admin2@email.com', 'ADMIN'),
(3, 'user1', 'userpass1', 'user1@email.com', 'USER'),
(4, 'user2', 'userpass2', 'user2@email.com', 'USER'),
(5, 'user3', 'userpass3', 'user3@email.com', 'USER');

INSERT INTO buses (busID, source, destination, departureDate, departureTime, totalSeats, ticketPrice) VALUES
('BUS100', 'Kuala Lumpur', 'Penang', '2025-09-10', '2025-09-10 08:00:00', 40, 45.00),
('BUS101', 'Johor Bahru', 'Kuala Lumpur', '2025-09-11', '2025-09-11 09:30:00', 35, 55.00),
('BUS102', 'Ipoh', 'Melaka', '2025-09-12', '2025-09-12 07:45:00', 30, 40.00);

INSERT INTO booking (bookingID, busID, userID, seatNumber, customerName, ticketPrice, bookingDateTime, STATUS) VALUES
('BK001', 'BUS100', 3, 12, 'user1', 45.00, '2025-09-02 10:00:00', 'CONFIRMED'),
('BK002', 'BUS101', 4, 7, 'user2', 55.00, '2025-09-02 11:30:00', 'CONFIRMED'),
('BK003', 'BUS102', 5, 18, 'user3', 40.00, '2025-09-02 09:00:00', 'PENDING'),
('BK004', 'BUS100', 4, 22, 'user2', 45.00, '2025-09-02 12:00:00', 'CANCELLED'),
('BK005', 'BUS101', 3, 5, 'user1', 55.00, '2025-09-02 13:00:00', 'CONFIRMED');
