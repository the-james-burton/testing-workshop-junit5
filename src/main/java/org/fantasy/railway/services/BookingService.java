package org.fantasy.railway.services;

import lombok.Setter;
import org.fantasy.railway.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BookingService {

    private static final Double PRICE_PER_MINUTE = 0.5d;

    @Setter
    NetworkService network;

    TimetableService timetable;
    List<Ticket> tickets;

    /**
     * calculates the route and returns a ticket containing the price, ready to be purchased
     *
     * @param from the station at the start of the journey
     * @param to the station at the end of the journey
     * @param passenger the passenger the ticket is to be valid for
     * @return
     */
    public Ticket ticketQuote(Station from, Station to, LocalTime when, Passenger passenger) {
        // TODO throw exception if route does not exist
        Journey journey = network.calculateRoute(from, to);

        if (journey == null || journey.getRoute().size() == 0) {
            throw new IllegalArgumentException(String.format("No travel possible from %s to %s", from, to));
        }

        Integer totalTime = journey.totalTime();
        Double price = totalTime * PRICE_PER_MINUTE * passenger.totalDiscount(when);
        return Ticket.builder()
                .passenger(passenger)
                .journey(journey)
                .price(price)
                .purchased(false)
                .build();
    }

    /**
     *
     * @param ticket the ticket to add a reservation for
     * @param when the time to book
     * @param preferences seating preferences
     * @return the ticket with the reservation added
     */
    public Ticket addReservation(Ticket ticket, LocalDateTime when, Seat preferences) {
        Service service = timetable.findSuitableService(ticket);
        Seat suitableSeat = service.getTrain().getCarriages().stream()
                .map(carriage -> carriage.getSeats())
                .flatMap(seats -> seats.stream())
                .filter(seat -> seat.isSuitableFor(preferences))
                .filter(seat -> seat.isAvailableAt(when))
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Service %s has not available seat with preferences %s", service, preferences)));
        ticket.setReservation(suitableSeat);
        service.getReservations().add(ticket);
        return ticket;
    }

    /**
     *
     * @param ticket the ticket that has been purchased
     * @param passenger the passenger purchasing the ticket
     */
    void purchaseTicket(Ticket ticket, Passenger passenger) {
        passenger.getTickets().add(ticket);
        ticket.getService().getReservations().add(ticket);
        // TODO - throw if service is already too full
    }
}
