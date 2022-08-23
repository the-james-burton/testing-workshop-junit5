package org.fantasy.railway.ui;

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

    void shouldViewNetwork() {
        // TODO EXERCISE 13
    }

    void shouldAddNewStation() {
        // TODO EXERCISE 13
    }

    void shouldLoadNetwork() {
        // TODO EXERCISE 13
    }
}
