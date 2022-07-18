package org.fantasy.railway.services;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Seat;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    /**
     * calculates the route and returns a ticket containing the price, ready to be purchased
     *
     * @param from      the station at the start of the journey
     * @param to        the station at the end of the journey
     * @param passenger the passenger the ticket is to be valid for
     * @return a ticket for the requested journey
     */
    Ticket ticketQuote(Station from, Station to, LocalDateTime when, Passenger passenger);

    /**
     * @param ticket      the ticket to add a reservation for
     * @param when        the time to book
     * @param preferences seating preferences
     * @return the ticket with the reservation added
     */
    Ticket addReservation(Ticket ticket, LocalDateTime when, Seat preferences);

    /**
     * @param ticket    the ticket that has been purchased
     * @param passenger the passenger purchasing the ticket
     */
    void purchaseTicket(Ticket ticket, Passenger passenger);

    /**
     * @return a List of all tickets
     */
    List<Ticket> getTickets();

    void setNetwork(NetworkService network);

    /**
     * @param id the id of the Ticket to lookup
     * @return the Ticket with the given id
     */
    Ticket getById(Integer id);
}
