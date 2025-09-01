package gui;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import javax.swing.*;
import dao.ConnectionProvider;

public class BookingForm extends JFrame implements ActionListener
{
    JLabel BF_Title, CustomerName, Source, Destination, DepartureDate, DepartureTime, ArrivalTime, SeatNumber, Price;
    JTextField tf_cust_name;
    JComboBox source_list, destination_list, seatnumber_list;
    JPanel seat_panel;
    JPanel p;
    JButton btn_book;
    String ID;
    JDateChooser dateChooser; // Add this line

    public BookingForm(String UserID, String busID, String source, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int seats, double ticketPrice)
    {
        p = new JPanel();
        ID = UserID;
        BF_Title = new JLabel("Bus Ticket Booking");

        CustomerName = new JLabel("Name: ");
        tf_cust_name = new JTextField();

        Source = new JLabel("From : ");
        source_list = new JComboBox(new String[] {source});

        Destination = new JLabel("To : ");
        destination_list = new JComboBox(new String[] {destination});

        SeatNumber = new JLabel("Seat Number: ");
        seatnumber_list = new JComboBox(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"});

        DepartureDate = new JLabel("Departure Date: "); // Add this line
        dateChooser = new JDateChooser(); // Add this line

        DepartureTime = new JLabel("Departure Time: ");
        DepartureTime.setText(departureTime.toString());

        ArrivalTime = new JLabel("Arrival Time: ");
        ArrivalTime.setText(arrivalTime.toString());

        btn_book = new JButton("BOOK TICKET");

        CustomerName.setBounds(20, 20, 100, 30);
        Source.setBounds(20, 40, 100, 30);
        Destination.setBounds(20, 60, 100, 30);
        SeatNumber.setBounds(20, 80, 100, 30);
        DepartureDate.setBounds(20, 100, 100, 30); // Add this line
        dateChooser.setBounds(140, 100, 100, 30); // Add this line
        DepartureTime.setBounds(20, 120, 100, 30);
        ArrivalTime.setBounds(20, 140, 100, 30);
        tf_cust_name.setBounds(140, 20, 100, 30);
        source_list.setBounds(140, 40, 100, 30);
        destination_list.setBounds(140, 60, 100, 30);
        seatnumber_list.setBounds(140, 80, 100, 30);
        btn_book.setBounds(80, 220, 120, 30);

        p.add(CustomerName);
        p.add(tf_cust_name);
        p.add(Source);
        p.add(source_list);
        p.add(Destination);
        p.add(destination_list);
        p.add(SeatNumber);
        p.add(seatnumber_list);
        p.add(DepartureDate); // Add this line
        p.add(dateChooser); // Add this line
        p.add(DepartureTime);
        p.add(ArrivalTime);
        p.add(btn_book);

        btn_book.addActionListener(this);
        add(p);
        p.setLayout(null);
        setVisible(true);
        setSize(600, 600);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == btn_book)
        {
            int x = 0;

            Connection con = ConnectionProvider.getConnection();
            String CustomerName = tf_cust_name.getText();

            try
            {
                 PreparedStatement ps = con.prepareStatement("insert into booking(bookingID, busID, UserID, SeatNumber, CustomerName, ticketPrice) values(?,?,?,?,?,?)");

                ps.setString(1, ID);
                String busID = null;
                ps.setString(2, busID);
                String UserID = null;
                ps.setString(3, UserID);
                ps.setString(4, seatnumber_list.getSelectedItem().toString());
                ps.setString(5, CustomerName);
                double ticketPrice = 0;
                ps.setDouble(6, ticketPrice);

                ps.executeUpdate();
                x++;
                if (x > 0) {
                    JOptionPane.showMessageDialog(btn_book, "Book Ticket");
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
        else
        {
            JOptionPane.showMessageDialog(btn_book, "Something went Wrong");
        }
    }
}