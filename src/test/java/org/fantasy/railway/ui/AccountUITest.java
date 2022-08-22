package org.fantasy.railway.ui;

import org.fantasy.railway.model.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.newAdult;
import static org.fantasy.railway.util.TestUtils.newPensioner;
import static org.fantasy.railway.util.TestUtils.newYoungPerson;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    void setup() {
        super.setup();
    }

    @Test
    void shouldViewPassengers() {
        in = new ByteArrayInputStream("1\n".getBytes());
        Scanner scanner = new Scanner(in);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(newYoungPerson());
        passengers.add(newAdult());
        passengers.add(newPensioner());

        when(accounts.getPassengers()).thenReturn(passengers);

        accountUI.displayMenu(scanner);

        String output = outStream.toString();
        assertThat(output)
                .isNotEmpty()
                .contains(passengers.get(0).toString());
    }

    @Test
    void shouldAddNewPassenger() {
        in = new ByteArrayInputStream("2\nAlice\n2000-01-01\n".getBytes());
        Scanner scanner = new Scanner(in);

        accountUI.displayMenu(scanner);

        verify(accounts).addPassenger(inputs.capture());

        assertThat(inputs.getValue().poll()).isEqualTo("Alice");
        assertThat(inputs.getValue().poll()).isEqualTo("2000-01-01");
    }

    @Test
    void shouldAddConcessionToPassenger() {
        in = new ByteArrayInputStream("3\n2\nYOUNG_PERSONS_RAILCARD\n".getBytes());
        Scanner scanner = new Scanner(in);
        Passenger passenger = newYoungPerson();

        when(accounts.getById(2)).thenReturn(passenger);

        accountUI.displayMenu(scanner);

        String output = outStream.toString();
        String expected = String.format("Concession YOUNG_PERSONS_RAILCARD added to %s", passenger.toString());
        assertThat(output)
                .isNotEmpty()
                .contains(expected);
    }

    @Test
    void shouldNotAddConcessionToPassengerNotQualifying() {
        in = new ByteArrayInputStream("3\n2\nPENSIONER_DISCOUNT\n".getBytes());
        Scanner scanner = new Scanner(in);
        Passenger passenger = newYoungPerson();

        when(accounts.getById(2)).thenReturn(passenger);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                accountUI.displayMenu(scanner)
        );

        String expected = "Passenger 2 does not qualify for concession PENSIONER_DISCOUNT";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldLoadPassengers() {
        in = new ByteArrayInputStream("4\nfilename.csv\n".getBytes());
        Scanner scanner = new Scanner(in);

        accountUI.displayMenu(scanner);

        verify(accounts).loadPassengers(inputString.capture());

        assertThat(inputString.getValue()).isEqualTo("filename.csv");
    }

    @Test
    void shouldRemovePassenger() {
        ArgumentCaptor<Passenger> passengerCaptor = ArgumentCaptor.forClass(Passenger.class);
        in = new ByteArrayInputStream("5\n2\n".getBytes());
        Scanner scanner = new Scanner(in);

        Passenger passenger = newYoungPerson();
        when(accounts.getById(passenger.getId())).thenReturn(passenger);

        accountUI.displayMenu(scanner);

        verify(accounts).removePassenger(passengerCaptor.capture());
        assertThat(passengerCaptor.getValue()).isEqualTo(passenger);
    }

}
