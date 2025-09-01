package gui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class BusUser {
    private final String busID;
    private final String source;
    private final String destination;
    private final LocalDate departureDate;
    private final LocalTime departureTime;
    private final int totalSeats;
    private final double ticketPrice;
    private final Set<Integer> bookedSeats = new HashSet<>();

    public BusUser(String busID, String source, String destination, LocalDate departureDate, LocalTime departureTime, int totalSeats, double ticketPrice) {
        this.busID = busID;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.totalSeats = totalSeats;
        this.ticketPrice = ticketPrice;
    }

    public String getBusID() {
        return busID;
    }

    public String getBusInfo() {
        return String.format("%s - %s | Seats Available: %d | Departure Date: %s | Departure Time: %s | RM%.2f", source, destination, totalSeats, departureDate, departureTime, ticketPrice);
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}