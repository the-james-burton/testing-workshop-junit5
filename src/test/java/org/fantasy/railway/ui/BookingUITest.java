package org.fantasy.railway.ui;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingUITest extends BaseUITest {

    @InjectMocks
    BookingUI bookingUI;

    @Override
    BaseUI getUI() {
        return bookingUI;
    }

    void shouldViewTickets() {
        // TODO EXERCISE 14
    }

    void shouldPurchaseNewTicket() {
        // TODO EXERCISE 14
    }
}
