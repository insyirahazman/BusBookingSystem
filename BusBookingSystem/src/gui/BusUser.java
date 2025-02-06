package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BusUser {
    private String busID;
    private String source;
    private String destination;
    private LocalDate departureDate;  // Change to LocalDate
    private LocalDateTime departureTime;
    private int totalSeats;
    private double ticketPrice;
    private Set<Integer> bookedSeats = new HashSet<>();

    public BusUser(String busID, String source, String destination, LocalDate departureDate, LocalDateTime departureTime, int totalSeats, double ticketPrice) {
        this.busID = busID;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;  // Initialize this field
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.ticketPrice = ticketPrice;
    }

    public String getBusID() {
        return busID;
    }

    public String getBusInfo() {
        return String.format("%s - %s | Departure Date: %s | Departure Time: %s | RM%.2f", source, destination, departureDate, departureTime, ticketPrice);
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}