package org.fantasy.railway.service;

import org.fantasy.railway.model.Ticket;
import org.fantasy.railway.services.BookingServiceImpl;
import org.fantasy.railway.services.NetworkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

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
        // TODO EXERCISE 5
        // HINT: Use Mockito.when to mock the external call made to the NetworkService
    }

    @Test
    void shouldNotPurchaseTicketIfNoRoute() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldApplyDiscount() {
        // TODO EXERCISE 5
    }

}