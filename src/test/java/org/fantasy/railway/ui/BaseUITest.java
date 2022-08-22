package org.fantasy.railway.ui;

import org.fantasy.railway.RailwaySystem;
import org.fantasy.railway.services.AccountService;
import org.fantasy.railway.services.BookingService;
import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.TimetableService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseUITest {

    PrintStream out;
    ByteArrayOutputStream outStream;

    InputStream in;

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

    void setup() {
        // initialise the in / out...
        outStream = new ByteArrayOutputStream();
        out = new PrintStream(outStream);
        in = null;

        // set up the system with a complete set of mock services...
        system = new RailwaySystem();
        system.setAccounts(accounts);
        system.setBookings(bookings);
        system.setNetwork(network);
        system.setTimetable(timetable);

        getUI().setSystem(system);
        getUI().setOut(out);

    }

    @Test
    void shouldDisplayMenu() {
        in = new ByteArrayInputStream("X\n".getBytes());
        Scanner scanner = new Scanner(in);
        getUI().displayMenu(scanner);

        // useful for debugging...
        // System.out.println(outStream.toString());

        assertThat(outStream.toString())
                .isNotEmpty()
                .contains("Please select an option");
    }
}
