package org.fantasy.railway.service;

import org.fantasy.railway.model.Ticket;
import org.fantasy.railway.services.BookingServiceImpl;
import org.fantasy.railway.services.NetworkServiceImpl;
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

    @Test
    void shouldGetItems() {
        Ticket ticket = Ticket.builder().id(1).build();
        booking.getTickets().add(ticket);

        assertThat(booking.getItems()).hasSize(1);
    }

    void shouldPurchaseTicket() {
        // TODO EXERCISE 6
        // HINT: Use Mockito.when to mock the external call made to the NetworkService
    }

    void shouldNotPurchaseTicketIfNoRoute() {
        // TODO EXERCISE 6
    }

    void shouldApplyDiscount() {
        // TODO EXERCISE 6
    }

}