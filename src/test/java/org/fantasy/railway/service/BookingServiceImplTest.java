package org.fantasy.railway.service;

import org.fantasy.railway.model.Concession;
import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.model.Ticket;
import org.fantasy.railway.services.BookingServiceImpl;
import org.fantasy.railway.services.NetworkServiceImpl;
import org.fantasy.railway.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.newAdult;
import static org.fantasy.railway.util.TestUtils.newYoungPerson;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @InjectMocks
    BookingServiceImpl booking;

    @Mock
    NetworkServiceImpl network;

    @BeforeEach
    void setup() {
        // demonstrate that the booking service is new each time...
        // System.out.println(booking.toString());
    }

    @Test
    void shouldGetItems() {
        Ticket ticket = Ticket.builder().id(1).build();
        booking.getTickets().add(ticket);

        assertThat(booking.getItems()).hasSize(1);
    }


    @Test
    void shouldPurchaseTicket() {
        Passenger passenger = newAdult();
        List<Stop> route = new ArrayList<>(TestUtils.createTestRoute(Service.builder().build()));
        when(network.calculateRoute(any(), any()))
                .thenReturn(route);

        Station from = route.get(0).getStation();
        Station to = route.get(2).getStation();
        LocalDate validOn = LocalDate.of(2022, 5, 10);

        Ticket ticket = booking.purchaseTicket(from, to, validOn, passenger);

        assertThat(ticket.getFrom()).isEqualTo(from);
        assertThat(ticket.getTo()).isEqualTo(to);
        assertThat(ticket.getValidOn()).isEqualTo(validOn);
        assertThat(ticket.getValidOn()).isEqualTo(validOn);
        assertThat(ticket.getPassenger()).isEqualTo(passenger);
        assertThat(ticket.getPrice()).isEqualTo(BigDecimal.valueOf(4.5d).setScale(2, RoundingMode.HALF_UP));
        verify(network, times(1)).calculateRoute(any(), any());

    }

    @Test
    void shouldNotPurchaseTicketIfNoRoute() {
        Passenger passenger = newAdult();
        when(network.calculateRoute(any(), any()))
                .thenReturn(new ArrayList<>());

        Station from = Station.builder().name("from").build();
        Station to = Station.builder().name("to").build();
        LocalDate validOn = LocalDate.of(2022, 5, 10);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                booking.purchaseTicket(from, to, validOn, passenger)
        );

        String expected = "No travel possible from";
        String actual = exception.getMessage();

        assertThat(actual).startsWith(expected);
        verify(network, times(1)).calculateRoute(any(), any());
    }

    @Test
    void shouldApplyDiscount() {
        Passenger passenger = newYoungPerson();
        passenger.addConcession(Concession.YOUNG_PERSONS_RAILCARD);
        List<Stop> route = new ArrayList<>(TestUtils.createTestRoute(Service.builder().build()));
        when(network.calculateRoute(any(), any()))
                .thenReturn(route);

        Station from = route.get(0).getStation();
        Station to = route.get(2).getStation();
        LocalDate validOn = LocalDate.of(2022, 5, 10);

        Ticket ticket = booking.purchaseTicket(from, to, validOn, passenger);

        assertThat(ticket.getPrice()).isLessThan(BigDecimal.valueOf(4.5d));
        verify(network, times(1)).calculateRoute(any(), any());
    }

}