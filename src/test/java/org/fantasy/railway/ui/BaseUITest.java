package org.fantasy.railway.ui;

import org.fantasy.railway.RailwaySystem;
import org.fantasy.railway.services.AccountService;
import org.fantasy.railway.services.BookingService;
import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.TimetableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public abstract class BaseUITest {

    PrintStream out;
    ByteArrayOutputStream outStream;

    InputStream in;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    RailwaySystem system;

    @Mock
    AccountService accounts;

    @Mock
    BookingService bookings;

    @Mock
    NetworkService network;

    @Mock
    TimetableService timetable;

    abstract BaseUI getUI();

    @BeforeEach
    void setup() {
        // initialise the in / out...
        outStream = new ByteArrayOutputStream();
        out = new PrintStream(outStream);
        in = null;

        when(system.getAccounts()).thenReturn(accounts);
        when(system.getBookings()).thenReturn(bookings);
        when(system.getNetwork()).thenReturn(network);
        when(system.getTimetable()).thenReturn(timetable);

        // inject the mocks into whatever UI subclass test is being run...
        //getUI().setSystem(system);
        getUI().setOut(out);

    }

    @Test
    void shouldDisplayMenu() {
        in = new ByteArrayInputStream("x\n".getBytes());
        Scanner scanner = new Scanner(in);
        getUI().displayMenu(scanner);

        assertThat(outStream.toString())
                .isNotEmpty()
                .contains("Please select an option");
    }
}
