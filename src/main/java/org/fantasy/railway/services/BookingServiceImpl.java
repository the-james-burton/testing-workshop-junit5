package org.fantasy.railway.services;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.model.Ticket;
import org.fantasy.railway.util.RailwayUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookingServiceImpl extends BaseService<Ticket> implements BookingService {

    private static final Double PRICE_PER_MINUTE = 0.5d;

    @Setter
    NetworkService network;

    TimetableServiceImpl timetable;

    @Getter
    List<Ticket> tickets = new ArrayList<>();

    @Override
    public Ticket purchaseTicket(Station from, Station to, LocalDateTime when, Passenger passenger) {
        List<Stop> route = network.calculateRoute(from, to);

        Preconditions.checkArgument(!route.isEmpty(),
                "No travel possible from %s to %s", from, to);

        Integer totalTime = RailwayUtils.totalTime(route);
        Double discount = 1.0d - passenger.totalDiscount(when);

        Double price = totalTime * PRICE_PER_MINUTE * discount;
        Ticket ticket = Ticket.builder()
                .passenger(passenger)
                .from(from)
                .to(to)
                .validOn(when.toLocalDate())
                .price(price)
                .build();

        tickets.add(ticket);
        passenger.getTickets().add(ticket);
        return ticket;

    }

    @Override
    List<Ticket> getItems() {
        return tickets;
    }
}
