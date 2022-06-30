package org.fantasy.railway.services;

import org.fantasy.railway.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {

    private static final Double PRICE_PER_MINUTE = 0.5d;

    NetworkService networkService;

    List<Ticket> tickets;

    /**
     * calculates the route and returns a ticket containing the price, ready to be purchased
     *
     * @param from the station at the start of the journey
     * @param to the station at the end of the journey
     * @param passenger the passenger the ticket is to be valid for
     * @return
     */
    Ticket ticketQuote(Station from, Station to, Passenger passenger) {
        // TODO throw exception if route does not exist
        Journey journey = networkService.calculateRoute(from, to);
        Integer totalTime = journey.totalTime();
        Double price = totalTime * PRICE_PER_MINUTE;
        // TODO adjust price according to passenger concessions
        return Ticket.builder()
                .passenger(passenger)
                .journey(journey)
                .price(price)
                .purchased(false)
                .build();
    }

    Ticket addReservation(LocalDateTime when, Seat preferences) {
        // TODO find available seat based on preferences
        return null;
    }

    /**
     *
     * @param ticket the ticket that has been purchased
     * @param passenger the passenger purchasing the ticket
     */
    void purchaseTicket(Ticket ticket, Passenger passenger) {
        passenger.getTickets().add(ticket);
        ticket.getService().getTickets().add(ticket);
        // TODO - throw if service is already too full
    }
}
