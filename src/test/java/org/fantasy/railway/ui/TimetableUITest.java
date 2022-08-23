package org.fantasy.railway.ui;

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

    void shouldListServices() {
        // TODO EXERCISE 14
    }

    void shouldShowDispatchedServices() {
        // TODO EXERCISE 14
    }

    void shouldAddNewService() {
        // TODO EXERCISE 14
    }

}
