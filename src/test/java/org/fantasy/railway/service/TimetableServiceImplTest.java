package org.fantasy.railway.service;

import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.TimetableServiceImpl;
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

    void shouldCreateNewServices() {
        // TODO EXERCISE 7
    }

    void shouldNotCreateNewServicesIfRouteExists() {
        // TODO EXERCISE 7
    }

    void shouldSkipAndRemoveEmptyServiceWhenDispatching() {
        // TODO EXERCISE 7
    }

    void shouldCreateNewServicesFromStringInput() {
        // TODO EXERCISE 7
    }

    void shouldDispatchServices() {
        // TODO EXERCISE 15
    }

    void shouldLoadServicesFromFileFullyMocked() {
        // TODO EXERCISE 8
    }

}
