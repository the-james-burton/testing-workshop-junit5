package org.fantasy.railway.ui;

import org.fantasy.railway.RailwaySystem;
import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.services.AccountService;
import org.fantasy.railway.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.newYoungPerson;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountUITest {

    @Captor
    ArgumentCaptor<Queue<String>> inputs;

    @Captor
    ArgumentCaptor<String> inputString;

    @Captor
    ArgumentCaptor<Integer> inputInteger;

    PrintStream out;
    ByteArrayOutputStream outStream;

    InputStream in;

    @InjectMocks
    AccountUI accountUI;

    RailwaySystem system;

    @Mock
    AccountService accounts;

    @BeforeEach
    void setup() {
        outStream = new ByteArrayOutputStream();
        out = new PrintStream(outStream);
        in = null;
        system = new RailwaySystem();
        system.setAccounts(accounts);

        accountUI = new AccountUI();
        accountUI.setSystem(system);
        accountUI.setOut(out);
    }

    @Test
    void shouldDisplayMenu() {
        in = new ByteArrayInputStream("X\n".getBytes());
        Scanner scanner = new Scanner(in);
        accountUI.displayMenu(scanner);

        String output = outStream.toString();
        assertThat(output)
                .isNotEmpty()
                .contains("Please select an option");
    }

    @Test
    void shouldViewPassengers() {
        in = new ByteArrayInputStream("1\n".getBytes());
        Scanner scanner = new Scanner(in);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(newYoungPerson());
        passengers.add(TestUtils.newAdult());
        passengers.add(TestUtils.newPensioner());

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

        String output = outStream.toString();
        assertThat(inputs.getValue().poll()).isEqualTo("Alice");
        assertThat(inputs.getValue().poll()).isEqualTo("2000-01-01");
    }

    @Test
    void shouldLoadPassengers() {
        in = new ByteArrayInputStream("3\nfilename.csv\n".getBytes());
        Scanner scanner = new Scanner(in);

        accountUI.displayMenu(scanner);

        verify(accounts).loadPassengers(inputString.capture());

        String output = outStream.toString();
        assertThat(inputString.getValue()).isEqualTo("filename.csv");
    }

    @Test
    void shouldRemovePassenger() {
        ArgumentCaptor<Passenger> captor = ArgumentCaptor.forClass(Passenger.class);
        in = new ByteArrayInputStream("4\n2\n".getBytes());
        Scanner scanner = new Scanner(in);

        Passenger passenger = newYoungPerson();
        when(accounts.getById(passenger.getId())).thenReturn(passenger);

        accountUI.displayMenu(scanner);

        verify(accounts).removePassenger(captor.capture());
        assertThat(captor.getValue()).isEqualTo(passenger);
        String output = outStream.toString();
    }

}
