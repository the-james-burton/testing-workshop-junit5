package org.fantasy.railway.service;

import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.TimetableServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimetableServiceImplTest {

    @Spy
    @InjectMocks
    TimetableServiceImpl timetable;

    @Mock
    NetworkService network;

    @Test
    void shouldCreateNewServices() {
        // TODO EXERCISE 6
    }

    @Test
    void shouldNotCreateNewServicesIfRouteExists() {
        // TODO EXERCISE 6
    }

    @Test
    void shouldLoadServicesFromFileFullyMocked() {
        // TODO EXERCISE 7
    }

    @Test
    void shouldSkipAndRemoveEmptyServiceWhenDispatching() {
        // TODO EXERCISE 6
    }

    @Test
    void shouldCreateNewServicesFromStringInput() {
        // TODO EXERCISE 6
    }

    @Test
    void shouldDispatchServices() {
        // TODO EXERCISE 14
    }

}
