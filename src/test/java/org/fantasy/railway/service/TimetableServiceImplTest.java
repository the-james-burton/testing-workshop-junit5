package org.fantasy.railway.service;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.TimetableServiceImpl;
import org.fantasy.railway.util.Now;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Queue;

import static java.time.Clock.fixed;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.createTestRoute;

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

        // TODO exercise about time based testing

        Service service = Service.builder().id(1).build();
        LinkedList<Stop> route = createTestRoute(service);
        Stop first = route.get(0);
        Stop second = route.get(1);
        Stop third = route.get(2);
        service.setRoute(route);

        timetable.getServices().add(service);

        // set the time to be in the middle of the service route...
        Instant startTime = Instant.parse("2022-05-10T10:02:00Z");
        Now.setClock(fixed(startTime, ZoneOffset.UTC));

        timetable.dispatch();

        Queue<Stop> dispatched = timetable.getDispatched();
        assertThat(dispatched)
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(first);

        Now.setClock(fixed(startTime.plus(4, ChronoUnit.MINUTES), ZoneOffset.UTC));
        timetable.dispatch();
        assertThat(dispatched).containsExactly(first, second);

        Now.setClock(fixed(startTime.plus(15, ChronoUnit.MINUTES), ZoneOffset.UTC));
        timetable.dispatch();

        assertThat(dispatched).containsExactly(first, second, third);

    }

}
