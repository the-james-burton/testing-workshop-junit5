package org.fantasy.railway.ui;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.firstStop;
import static org.fantasy.railway.util.TestUtils.newAdult;
import static org.fantasy.railway.util.TestUtils.thirdStop;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingUITest extends BaseUITest {

    @InjectMocks
    BookingUI bookingUI;

    @Override
    BaseUI getUI() {
        return bookingUI;
    }

    @BeforeEach
    void setup() {
        super.setup();
        bookingUI = new BookingUI();
        bookingUI.setSystem(system);
        bookingUI.setOut(out);
    }

    @Test
    void shouldViewTickets() {
        in = new ByteArrayInputStream("1\n".getBytes());
        Scanner scanner = new Scanner(in);

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(Ticket.builder().id(1).build());
        tickets.add(Ticket.builder().id(2).build());
        tickets.add(Ticket.builder().id(3).build());

        when(bookings.getTickets()).thenReturn(tickets);

        bookingUI.displayMenu(scanner);

        String output = outStream.toString();
        assertThat(output)
                .isNotEmpty()
                .contains(tickets.get(0).toString());
    }

    @Test
    void shouldPurchaseNewTicket() {
        in = new ByteArrayInputStream("2\nFirst\nThird\n2022-05-10\n3\n".getBytes());
        Scanner scanner = new Scanner(in);

        Station from = firstStop().getStation();
        Station to = thirdStop().getStation();
        LocalDate when = LocalDate.of(2022, 5, 10);
        Passenger passenger = newAdult();

        ArgumentCaptor<Station> fromCaptor = ArgumentCaptor.forClass(Station.class);
        ArgumentCaptor<Station> toCaptor = ArgumentCaptor.forClass(Station.class);
        ArgumentCaptor<LocalDate> whenCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<Passenger> passengerCaptor = ArgumentCaptor.forClass(Passenger.class);

        when(network.getStationOrThrow("First")).thenReturn(from);
        when(network.getStationOrThrow("Third")).thenReturn(to);
        when(accounts.getById(passenger.getId())).thenReturn(passenger);

        bookingUI.displayMenu(scanner);

        verify(bookings).purchaseTicket(fromCaptor.capture(), toCaptor.capture(), whenCaptor.capture(), passengerCaptor.capture());

        assertThat(fromCaptor.getValue()).isEqualTo(from);
        assertThat(toCaptor.getValue()).isEqualTo(to);
        assertThat(whenCaptor.getValue()).isEqualTo(when);
        assertThat(passengerCaptor.getValue()).isEqualTo(passenger);
    }
}
