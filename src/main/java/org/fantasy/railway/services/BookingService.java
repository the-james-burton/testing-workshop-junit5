package org.fantasy.railway.services;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;

public class BookingService {

    /**
     *
     * @param from the station at the start of the journey
     * @param to the station at the end of the journey
     * @param passenger the passenger the ticket is to be valid for
     * @return
     */
    Ticket ticketQuote(Station from, Station to, Passenger passenger) {
        // TODO
        return null;
    }

    /**
     *
     * @param ticket the ticket that has been purchased
     * @param service the service the ticket is to be added to
     */
    void purchaseTicket(Ticket ticket, Passenger passenger) {
        passenger.getTickets().add(ticket);
        ticket.getService().getTickets().add(ticket);
        // TODO - throw if service is already too full
    }
}
