package org.fantasy.railway.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NetworkUITest extends BaseUITest {

    @InjectMocks
    NetworkUI networkUI;

    @Override
    BaseUI getUI() {
        return networkUI;
    }

    @Test
    void shouldViewNetwork() {
        // TODO EXERCISE 12
    }

    @Test
    void shouldAddNewStation() {
        // TODO EXERCISE 12
    }

    @Test
    void shouldLoadNetwork() {
        // TODO EXERCISE 12
    }
}
