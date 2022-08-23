package org.fantasy.railway.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;

@ExtendWith(MockitoExtension.class)
class AccountUITest extends BaseUITest {

    @Captor
    ArgumentCaptor<Queue<String>> inputs;

    @Captor
    ArgumentCaptor<String> inputString;

    @InjectMocks
    AccountUI accountUI;

    @Override
    BaseUI getUI() {
        return accountUI;
    }

    @Test
    void shouldViewPassengers() {
        // TODO EXERCISE 13
    }

    @Test
    void shouldAddNewPassenger() {
        // TODO EXERCISE 13
    }

    @Test
    void shouldAddConcessionToPassenger() {
        // TODO EXERCISE 13
    }

    @Test
    void shouldNotAddConcessionToPassengerNotQualifying() {
        // TODO EXERCISE 13
    }

    @Test
    void shouldLoadPassengers() {
        // TODO EXERCISE 13
    }

    @Test
    void shouldRemovePassenger() {
        // TODO EXERCISE 13
    }

}
