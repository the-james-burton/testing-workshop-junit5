package org.fantasy.railway.service;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;
import org.fantasy.railway.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceImplTest {

    AccountServiceImpl accounts;

    @BeforeEach
    void setup() {
        accounts = new AccountServiceImpl();
    }

    @Test
    void shouldHaveEmptyPassengerListOnCreation() {
        assertThat(accounts.getPassengers()).isEmpty();
    }

    @Test
    void shouldAddPassenger() {
        String name = "Alice";
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);

        Passenger passenger = accounts.addPassenger(name, dateOfBirth);

        assertThat(passenger.getName()).isEqualTo(name);
        assertThat(passenger.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(passenger.getConcessions()).isEmpty();
        assertThat(passenger.getId()).isEqualTo(1);
        assertThat(accounts.getPassengers()).contains(passenger);
        assertThat(accounts.getPassengers()).hasSize(1);
    }

    @Test
    void shouldAddPassengerFromStringInput() {
        String name = "Alice";
        String dateOfBirth = "2000-01-01";

        Queue<String> inputs = new LinkedList<>();
        inputs.add(name);
        inputs.add(dateOfBirth);

        Passenger passenger = accounts.addPassenger(inputs);

        assertThat(passenger.getName()).isEqualTo(name);
        assertThat(passenger.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(passenger.getConcessions()).isEmpty();
        assertThat(passenger.getId()).isEqualTo(1);
        assertThat(accounts.getPassengers()).contains(passenger);
        assertThat(accounts.getPassengers()).hasSize(1);
    }

    @Test
    void shouldRemovePassenger() {
        Passenger passenger = accounts.addPassenger("Alice", LocalDate.of(2000, 1, 1));
        accounts.removePassenger(passenger);

        assertThat(accounts.getPassengers()).isEmpty();
    }

    @Test
    void shouldNotRemovePassengerWithFutureTicket() {
        Passenger passenger = accounts.addPassenger("Alice", LocalDate.of(2000, 1, 1));

        Ticket ticket = Ticket.builder()
                .passenger(passenger)
                .from(Station.builder().build())
                .validOn(LocalDate.now().plusDays(1))
                .build();

        passenger.getTickets().add(ticket);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                accounts.removePassenger(passenger)
        );

        String expected = "Passenger holds future ticket";
        String actual = exception.getMessage();

        assertThat(actual).startsWith(expected);
        assertThat(accounts.getPassengers()).isNotEmpty();
    }

    @Test
    void shouldLoadPassengersFromFile() {
        accounts.loadPassengers("test-passengers.csv");

        assertThat(accounts.getPassengers()).isNotEmpty();
        assertThat(accounts.getPassengers()).hasSize(3);
    }

}
