package org.fantasy.railway;

import org.fantasy.railway.services.AccountService;
import org.fantasy.railway.services.BookingService;
import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.TimetableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RailwaySystemTest {

    @Mock
    AccountService accounts;

    @Mock
    BookingService bookings;

    @Mock
    NetworkService network;

    @Mock
    TimetableService timetable;

    @Spy
    @InjectMocks
    RailwaySystem system;

    @Test
    void shouldInitialize() {
        doNothing().when(system).bootstrap();

        system.initialize();

        // check that the mocks have been overwritten...
        assertThat(system.getAccounts()).isNotEqualTo(accounts);
        assertThat(system.getBookings()).isNotEqualTo(bookings);
        assertThat(system.getNetwork()).isNotEqualTo(network);
        assertThat(system.getTimetable()).isNotEqualTo(timetable);

        verify(system, times(1)).bootstrap();
    }

    @Test
    void shouldBootstrap() {
        system.bootstrap();

        verify(accounts, times(1)).loadPassengers("passengers.csv");
        verify(network, times(1)).loadNetwork("network.csv");
        verify(timetable, times(1)).loadServices("services.csv");
    }

    @Test
    void shouldShutdown() {
        system.shutdown();

        verify(timetable, times(1)).shutdown();
    }
}
