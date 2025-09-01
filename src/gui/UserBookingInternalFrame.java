package gui;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class UserBookingInternalFrame extends JInternalFrame {
    private final int userID;
    private JDateChooser dateChooser;
    private DefaultListModel<String> busListModel;
    private JList<String> busListUI;
    private List<BusUser> buses = new ArrayList<>();

    public UserBookingInternalFrame(int userID) {
        super("User Booking", true, true, true, true);
        this.userID = userID;
        initComponents();
        setSize(800, 600);
        setVisible(true);
        
        SwingUtilities.invokeLater(() -> {
            if (getParent() != null) {
                setLocation(
                    (getParent().getWidth() - getWidth()) / 2,
                    (getParent().getHeight() - getHeight()) / 2
                );
            }
        });
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Date panel
        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(new JLabel("Select Date:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        datePanel.add(dateChooser);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchBuses());
        datePanel.add(searchButton);

        mainPanel.add(datePanel, BorderLayout.NORTH);

        // Bus list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(new TitledBorder("Available Buses"));
        busListModel = new DefaultListModel<>();
        busListUI = new JList<>(busListModel);
        busListUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(busListUI);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(listPanel, BorderLayout.CENTER);

        // Booking button
        JPanel buttonPanel = new JPanel();
        JButton bookButton = new JButton("Book Seat");
        bookButton.addActionListener(e -> bookSeat());
        buttonPanel.add(bookButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void searchBuses() {
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select a date!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(dateChooser.getDate());
        buses = loadBusesFromDatabase(date);
        updateBusList();
    }

    private List<BusUser> loadBusesFromDatabase(String date) {
        List<BusUser> buses = new ArrayList<>();
        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM buses WHERE DATE(departureDate) = ?")) {
            
            ps.setString(1, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BusUser bus = new BusUser(
                        rs.getString("busID"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getDate("departureDate").toLocalDate(),
                        rs.getTime("departureTime").toLocalTime(),
                        rs.getInt("totalSeats"),
                        rs.getDouble("ticketPrice")
                    );
                    buses.add(bus);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading buses: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        return buses;
    }

    private void updateBusList() {
        busListModel.clear();
        for (BusUser bus : buses) {
            busListModel.addElement(bus.getBusInfo());
        }
    }

    private void bookSeat() {
        int selectedIndex = busListUI.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a bus first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        BusUser selectedBus = buses.get(selectedIndex);
        showSeatDialog(selectedBus);
    }

    private void showSeatDialog(BusUser bus) {
        JDialog seatDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Seat Selection - " + bus.getBusID(), true);
        seatDialog.setLayout(new GridBagLayout());
        
        JPanel seatPanel = new JPanel(new GridLayout(0, 4, 5, 5));
        seatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<Integer> bookedSeats = getBookedSeats(bus.getBusID());

        for (int i = 1; i <= bus.getTotalSeats(); i++) {
            JButton seatButton = new JButton(String.valueOf(i));
            seatButton.setPreferredSize(new Dimension(60, 40));
            
            if (bookedSeats.contains(i)) {
                seatButton.setEnabled(false);
                seatButton.setBackground(Color.RED);
                seatButton.setToolTipText("Seat " + i + " is already booked");
            } else {
                seatButton.setBackground(Color.GREEN);
                final int seatNumber = i;
                seatButton.addActionListener(e -> {
                    if (bookSelectedSeat(bus, seatNumber)) {
                        seatButton.setEnabled(false);
                        seatButton.setBackground(Color.RED);
                        seatButton.setToolTipText("You booked this seat");
                    }
                });
            }
            seatPanel.add(seatButton);
        }

        seatDialog.add(seatPanel);
        seatDialog.pack();
        seatDialog.setLocationRelativeTo(this);
        seatDialog.setVisible(true);
    }
    
    private List<Integer> getBookedSeats(String busID) {
        List<Integer> bookedSeats = new ArrayList<>();
        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement("SELECT seatNumber FROM bookings WHERE busID = ?")) {
            
            ps.setString(1, busID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookedSeats.add(rs.getInt("seatNumber"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading booked seats: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        return bookedSeats;
    }
    
    private boolean bookSelectedSeat(BusUser bus, int seatNumber) {
        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement("INSERT INTO bookings (busID, seatNumber, userID) VALUES (?, ?, ?)")) {
            
            ps.setString(1, bus.getBusID());
            ps.setInt(2, seatNumber);
            ps.setInt(3, this.userID); 

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Booking failed: " + e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}