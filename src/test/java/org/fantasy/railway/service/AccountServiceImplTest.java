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
import static org.fantasy.railway.util.TestUtils.newAdult;
import static org.fantasy.railway.util.TestUtils.newYoungPerson;
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
    void shouldGetById() {
        Passenger passenger1 = newAdult();
        Passenger passenger2 = newYoungPerson();

        accounts.getPassengers().add(passenger1);
        accounts.getPassengers().add(passenger2);

        assertThat(accounts.getById(passenger1.getId())).isEqualTo(passenger1);
        assertThat(accounts.getById(passenger2.getId())).isEqualTo(passenger2);
    }

    @Test
    void shouldNotGetByIdIfNotExists() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                accounts.getById(7)
        );

        String expected = "No item with id 7";
        String actual = exception.getMessage();

        assertThat(actual).startsWith(expected);
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
        assertThat(accounts.getPassengers())
                .contains(passenger)
                .hasSize(1);
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
        assertThat(accounts.getPassengers())
                .contains(passenger)
                .hasSize(1);
    }

    @Test
    void shouldLoadPassengersFromFile() {
        accounts.loadPassengers("test-passengers.csv");

        assertThat(accounts.getPassengers())
                .isNotEmpty()
                .hasSize(3);
    }

}
