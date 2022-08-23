package org.fantasy.railway.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;

@ExtendWith(MockitoExtension.class)
class TimetableUITest extends BaseUITest {

    @Captor
    ArgumentCaptor<Queue<String>> inputs;

    @InjectMocks
    TimetableUI timetableUI;

    @Override
    BaseUI getUI() {
        return timetableUI;
    }

    @Test
    void shouldListServices() {
        // TODO EXERCISE 14
    }

    @Test
    void shouldShowDispatchedServices() {
        // TODO EXERCISE 14
    }

    @Test
    void shouldAddNewService() {
        // TODO EXERCISE 14
    }

}
