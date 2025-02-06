package gui;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BusUser {
    private String busID;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;  // Add this field
    private int totalSeats;
    private double ticketPrice;
    private Set<Integer> bookedSeats = new HashSet<>();

    public BusUser(String busID, String source, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, int totalSeats, double ticketPrice) {
        this.busID = busID;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;  // Initialize this field
        this.totalSeats = totalSeats;
        this.ticketPrice = ticketPrice;
    }

    public String getBusID() {
        return busID;
    }

    public String getBusInfo() {
        return String.format("%s - %s | Departure: %s | Arrival: %s | RM%.2f", source, destination, departureTime, arrivalTime, ticketPrice);
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}