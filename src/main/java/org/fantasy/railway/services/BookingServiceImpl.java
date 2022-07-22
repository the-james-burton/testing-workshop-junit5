package org.fantasy.railway.services;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.fantasy.railway.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookingServiceImpl extends BaseService<Ticket> implements BookingService {

    private static final Double PRICE_PER_MINUTE = 0.5d;

    @Setter
    NetworkService network;

    TimetableServiceImpl timetable;

    @Getter
    List<Ticket> tickets = new ArrayList<>();

    @Override
    public Ticket ticketQuote(Station from, Station to, LocalDateTime when, Passenger passenger) {
        Journey journey = network.calculateRoute(from, to);

        Preconditions.checkArgument(!journey.getRoute().isEmpty(),
                "No travel possible from %s to %s", from, to);

        Integer totalTime = journey.totalTime();
        Double price = totalTime * PRICE_PER_MINUTE * (1.0d - passenger.totalDiscount(when));
        Ticket ticket = Ticket.builder()
                .passenger(passenger)
                .journey(journey)
                .price(price)
                .purchased(false)
                .build();

        tickets.add(ticket);
        return ticket;

    }

    @Override
    public Ticket addReservation(Ticket ticket, LocalDateTime when) {
        Service service = timetable.findSuitableService(ticket);
        Seat seat = service.getTrain().getCarriages().stream()
                .map(Carriage::getSeats)
                .flatMap(Collection::stream)
                .filter(s -> s.isAvailableAt(when))
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Service %s has no available seat", service)));

        ticket.setReservation(seat);
        service.getReservations().add(ticket);
        ticket.setService(service);

        return ticket;
    }

    @Override
    public void purchaseTicket(Ticket ticket, Passenger passenger) {
        Preconditions.checkArgument(ticket.getService() != null,
                "Ticket %s is not yet assigned to a service", ticket);
        Preconditions.checkArgument(timetable.isServiceFullyBooked(ticket.getService()),
                "Service %s is fully booked", ticket.getService());

        ticket.setPurchased(true);
        passenger.getTickets().add(ticket);
        ticket.getService().getReservations().add(ticket);
    }

    @Override
    List<Ticket> getItems() {
        return tickets;
    }
}
