-- Database creation script for Bus Booking System

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  role VARCHAR(10) NOT NULL
);

CREATE TABLE buses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  bus_number VARCHAR(20) NOT NULL,
  source VARCHAR(50),
  destination VARCHAR(50),
  seats INT
);

CREATE TABLE bookings (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  bus_id INT,
  booking_date DATE,
  seat_number INT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (bus_id) REFERENCES buses(id)
);

-- Sample data for Bus Booking System

INSERT INTO users (username, password, role) VALUES
('admin1', 'adminpass1', 'admin'),
('admin2', 'adminpass2', 'admin'),
('user1', 'userpass1', 'user'),
('user2', 'userpass2', 'user'),
('user3', 'userpass3', 'user');

INSERT INTO buses (bus_number, source, destination, seats) VALUES
('BUS100', 'Kuala Lumpur', 'Penang', 40),
('BUS101', 'Johor Bahru', 'Kuala Lumpur', 35),
('BUS102', 'Ipoh', 'Melaka', 30),
('BUS103', 'Kuantan', 'Kuala Lumpur', 45);

INSERT INTO bookings (user_id, bus_id, booking_date, seat_number) VALUES
(3, 1, '2025-09-05', 12),
(4, 2, '2025-09-06', 7),
(5, 3, '2025-09-07', 18),
(3, 4, '2025-09-08', 22),
(4, 1, '2025-09-09', 5);
