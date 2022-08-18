package org.fantasy.railway.services;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;

import java.time.LocalDate;
import java.util.List;

/**
 * Defines methods to sell tickets to passengers and retrieve them
 */
public interface BookingService {

    /**
     * calculates the route and returns a ticket containing the price, ready to be purchased
     *
     * @param from      the station at the start of the journey
     * @param to        the station at the end of the journey
     * @param passenger the passenger the ticket is to be valid for
     * @return a ticket for the requested journey
     */
    Ticket purchaseTicket(Station from, Station to, LocalDate when, Passenger passenger);

    /**
     * @return a List of all tickets
     */
    List<Ticket> getTickets();

    void setNetwork(NetworkService network);
}
