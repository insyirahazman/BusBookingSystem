# Bus Booking System

This is a Java-based Bus Booking System application. It allows users to book bus tickets, manage bookings, and provides admin functionalities for bus management.

## Features
- User registration and login
- Admin registration and login
- Book bus tickets
- View booking details
- Admin dashboard for bus management
- Database connectivity (MySQL)

## Project Structure
- `src/` - Source code (Java files)
- `build/` - Compiled classes
- `lib/` - External libraries (e.g., MySQL connector, JCalendar)
- `images/` - Image assets
- `build.xml` - Build configuration file

## Requirements
- Java 8 or above
- MySQL database
- JCalendar library (`jcalendar-1.4.jar`)
- MySQL Connector/J (`mysql-connector-j-9.2.0.jar`)
- jBCrypt library (`jbcrypt-0.4.jar`)

## How to Run
### Using NetBeans

1. Clone the repository.
2. Open the project folder in NetBeans (`File > Open Project`).
3. Ensure the required libraries (`jcalendar-1.4.jar`, `mysql-connector-j-9.2.0.jar`, `jbcrypt-0.4.jar`) are added to the project libraries.
4. Set up the MySQL database and update connection details in `ConnectionProvider.java`.
5. Build and run the project directly from NetBeans (`Run > Run Project`).

### Using Ant or Other IDEs

1. Clone the repository.
2. Set up the MySQL database and update connection details in `ConnectionProvider.java`.
3. Build the project using the provided `build.xml` (Ant) or your preferred IDE.
4. Run the `Main.java` file to start the application.

### Using VS Code or Terminal

1. Open the project folder in VS Code.
2. Make sure all required libraries (`jcalendar-1.4.jar`, `mysql-connector-j-9.2.0.jar`, `jbcrypt-0.4.jar`) are in the `lib` folder.
3. Open a terminal in the project root.
4. Compile all source files:
   ```powershell
   javac -cp "BusBookingSystem/lib/*" -d BusBookingSystem/build BusBookingSystem/src/dao/*.java BusBookingSystem/src/gui/*.java
   ```
5. Run the application: 
    ```powershell
    java -cp "BusBookingSystem/build;BusBookingSystem/lib/*" gui.Main
    ```

## License
This project is for educational purposes.
