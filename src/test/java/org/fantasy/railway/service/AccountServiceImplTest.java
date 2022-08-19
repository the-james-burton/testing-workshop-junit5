package org.fantasy.railway.service;

import org.fantasy.railway.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountServiceImplTest {

    AccountServiceImpl accounts;

    @BeforeEach
    void setup() {
        accounts = new AccountServiceImpl();
    }

    @Test
    void shouldHaveEmptyPassengerListOnCreation() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldGetById() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldNotGetByIdIfNotExists() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldAddPassenger() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldAddPassengerFromStringInput() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldRemovePassenger() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldNotRemovePassengerWithFutureTicket() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldLoadPassengersFromFile() {
        // TODO EXERCISE 4
    }

}
