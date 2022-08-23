package org.fantasy.railway.service;

import org.fantasy.railway.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;

class AccountServiceImplTest {

    AccountServiceImpl accounts;

    @BeforeEach
    void setup() {
        accounts = new AccountServiceImpl();
    }

    void shouldHaveEmptyPassengerListOnCreation() {
        // TODO EXERCISE 5
    }

    void shouldGetById() {
        // TODO EXERCISE 5
    }


    void shouldNotGetByIdIfNotExists() {
        // TODO EXERCISE 5
    }

    void shouldAddPassenger() {
        // TODO EXERCISE 5
    }

    void shouldAddPassengerFromStringInput() {
        // TODO EXERCISE 5
    }

    void shouldNotRemovePassengerWithFutureTicket() {
        // TODO EXERCISE 5
    }

    void shouldLoadPassengersFromFile() {
        // TODO EXERCISE 5
    }

}
