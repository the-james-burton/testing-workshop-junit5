package org.fantasy.railway.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RailwayUITest extends BaseUITest {

    @Mock
    AccountUI accountUI;

    @Mock
    BookingUI bookingUI;

    @Mock
    NetworkUI networkUI;

    @Mock
    TimetableUI timetableUI;

    @Spy
    @InjectMocks
    RailwayUI railwayUI;

    @Test
    void shouldInitialize() {
        railwayUI.initialize();

        // check that the mocks have been overwritten...
        assertThat(railwayUI.getAccountUI()).isNotEqualTo(accountUI);
        assertThat(railwayUI.getBookingUI()).isNotEqualTo(bookingUI);
        assertThat(railwayUI.getNetworkUI()).isNotEqualTo(networkUI);
        assertThat(railwayUI.getTimetableUI()).isNotEqualTo(timetableUI);

        // and contain the in / out we have injected...
        assertThat(railwayUI.getAccountUI().getOut()).isEqualTo(out);
        assertThat(railwayUI.getBookingUI().getOut()).isEqualTo(out);
        assertThat(railwayUI.getNetworkUI().getOut()).isEqualTo(out);
        assertThat(railwayUI.getTimetableUI().getOut()).isEqualTo(out);

        assertThat(railwayUI.getAccountUI().getSystem()).isEqualTo(system);
        assertThat(railwayUI.getBookingUI().getSystem()).isEqualTo(system);
        assertThat(railwayUI.getNetworkUI().getSystem()).isEqualTo(system);
        assertThat(railwayUI.getTimetableUI().getSystem()).isEqualTo(system);
    }

    @Override
    BaseUI getUI() {
        return railwayUI;
    }

    @Test
    void shouldDisplayTopMenu() {
        in = new ByteArrayInputStream("x\n".getBytes());
        Scanner scanner = new Scanner(in);

        railwayUI.topMenu(scanner);

        String output = outStream.toString();
        assertThat(output)
                .contains("Welcome to the Fantasy Railway System")
                .contains("Exiting System");
    }

    @Test
    void shouldSafelyHandleRuntimeException() {
        in = new ByteArrayInputStream("x\nx\n".getBytes());
        Scanner scanner = new Scanner(in);
        String message = "I have failed you";

        when(railwayUI.displayMenu(scanner))
                .thenThrow(new IllegalStateException(message))
                .thenReturn("x");

        railwayUI.topMenu(scanner);

        String output = outStream.toString();
        assertThat(output)
                .contains("ERROR")
                .contains(message)
                .contains("Exiting System");
    }

    @Test
    void shouldNavigateToAccountMenu() {
        in = new ByteArrayInputStream("1\n".getBytes());
        Scanner scanner = new Scanner(in);

        railwayUI.displayMenu(scanner);

        verify(accountUI, Mockito.times(1)).displayMenu(scanner);
    }

    @Test
    void shouldNavigateToBookingMenu() {
        in = new ByteArrayInputStream("2\n".getBytes());
        Scanner scanner = new Scanner(in);

        railwayUI.displayMenu(scanner);

        verify(bookingUI, Mockito.times(1)).displayMenu(scanner);
    }

    @Test
    void shouldNavigateToNetworkMenu() {
        in = new ByteArrayInputStream("3\n".getBytes());
        Scanner scanner = new Scanner(in);

        railwayUI.displayMenu(scanner);

        verify(networkUI, Mockito.times(1)).displayMenu(scanner);
    }

    @Test
    void shouldNavigateToTimetableMenu() {
        in = new ByteArrayInputStream("4\n".getBytes());
        Scanner scanner = new Scanner(in);

        railwayUI.displayMenu(scanner);

        verify(timetableUI, Mockito.times(1)).displayMenu(scanner);
    }
}
