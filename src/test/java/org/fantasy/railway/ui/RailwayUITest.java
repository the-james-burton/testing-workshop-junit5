package org.fantasy.railway.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
        // TODO EXERCISE 13
    }

    @Override
    BaseUI getUI() {
        return railwayUI;
    }

    void shouldDisplayTopMenu() {
        // TODO EXERCISE 13
    }

    void shouldSafelyHandleRuntimeException() {
        // TODO EXERCISE 13
        // hint : using a when().thenThrow() may help in this test
    }

    void shouldNavigateToAccountMenu() {
        // TODO EXERCISE 13
    }

    void shouldNavigateToBookingMenu() {
        // TODO EXERCISE 13
    }

    void shouldNavigateToNetworkMenu() {
        // TODO EXERCISE 13
    }

    void shouldNavigateToTimetableMenu() {
        // TODO EXERCISE 13
    }
}
