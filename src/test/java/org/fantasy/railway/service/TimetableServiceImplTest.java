package org.fantasy.railway.service;

import org.assertj.core.api.Assertions;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.TimetableServiceImpl;
import org.fantasy.railway.util.Now;
import org.fantasy.railway.util.RailwayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.time.Clock.fixed;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.createTestRoute;
import static org.fantasy.railway.util.TestUtils.firstStop;
import static org.fantasy.railway.util.TestUtils.secondStop;
import static org.fantasy.railway.util.TestUtils.thirdStop;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimetableServiceImplTest {

    @Spy
    @InjectMocks
    TimetableServiceImpl timetable;

    @Mock
    NetworkService network;

    @BeforeEach
    void setup() {
        timetable = Mockito.spy(new TimetableServiceImpl());
        timetable.setNetwork(network);
    }

    @Test
    void shouldCreateNewServices() {
        Station first = firstStop().getStation();
        Station second = secondStop().getStation();
        Station third = thirdStop().getStation();
        when(network.calculateRoute(first, third)).thenReturn(createTestRoute());
        when(network.distanceBetweenAdjacent(first, second)).thenReturn(14);
        when(network.distanceBetweenAdjacent(second, third)).thenReturn(17);

        Integer frequency = 60;
        List<Service> result = timetable.createNewServices(frequency, first, third);

        Assertions.assertThat(result).hasSize(23);
        verify(network, times(1)).calculateRoute(first, third);
        verify(network, times(2)).distanceBetweenAdjacent(any(), any());
    }

    @Test
    void shouldNotCreateNewServicesIfRouteExists() {
        Station first = firstStop().getStation();
        Station second = secondStop().getStation();
        Station third = thirdStop().getStation();
        when(network.calculateRoute(first, third)).thenReturn(createTestRoute());
        when(network.distanceBetweenAdjacent(first, second)).thenReturn(14);
        when(network.distanceBetweenAdjacent(second, third)).thenReturn(17);

        Integer frequency = 60;
        timetable.createNewServices(frequency, first, third);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                timetable.createNewServices(frequency, first, third)
        );

        String expected = "There is already a service defined with route ";
        String actual = exception.getMessage();

        assertThat(actual).startsWith(expected);
    }

    @Test
    void shouldCreateNewServicesFromStringInput() {

        // TODO EXERCISE about partial mocks/spy

        Integer frequency = 60;
        Station start = firstStop().getStation();
        Station finish = secondStop().getStation();
        Queue<String> inputs = new LinkedList<>(Arrays.asList(frequency.toString(), start.getName(), finish.getName()));

        // mock return objects...
        Service service = Service.builder().id(1).build();
        List<Service> expected = Arrays.asList(service);

        // replace real implementation with stubs..
        doReturn(expected).when(timetable).createNewServices(frequency, start, finish);
        when(network.getStationOrThrow(start.getName())).thenReturn(start);
        when(network.getStationOrThrow(finish.getName())).thenReturn(finish);

        List<Service> actual = timetable.createNewServices(inputs);

        verify(timetable, times(1)).createNewServices(frequency, start, finish);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldLoadServicesFromFileFullyMocked() {
        // mock returns from parsing the file...
        Queue<String> row1 = new LinkedList<>(Arrays.asList("one", "two", "three"));
        Queue<String> row2 = new LinkedList<>(Arrays.asList("four", "five", "six"));
        Queue<Queue<String>> parsedFile = new LinkedList<>(Arrays.asList(row1, row2));
        String filename = "dummy-filename.csv";

        // replace real implementation with stubs...
        doReturn(new LinkedList<>()).when(timetable).createNewServices(row1);
        doReturn(new LinkedList<>()).when(timetable).createNewServices(row2);

        // mock the static method that is used so it returns our mock objects...
        try (MockedStatic<RailwayUtils> utils = Mockito.mockStatic(RailwayUtils.class)) {
            utils.when(() -> RailwayUtils.parseFile(filename)).thenReturn(parsedFile);

            // execute the method under test...
            timetable.loadServices(filename);

            // verify the mocked static method was called...
            utils.verify(() -> RailwayUtils.parseFile(filename),
                    times(1)
            );

            // verify that the forEach made the expected calls...
            verify(timetable, times(1)).createNewServices(row1);
            verify(timetable, times(1)).createNewServices(row2);

        }

    }

    @Test
    void shouldDispatchServices() {
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

    @Test
    void shouldSkipAndRemoveEmptyServiceWhenDispatching() {
        Service service = Service.builder().id(1).build();
        timetable.getServices().add(service);

        timetable.dispatch();

        assertThat(timetable.getDispatched())
                .isNotNull()
                .isEmpty();

        assertThat(timetable.getServices())
                .isNotNull()
                .isEmpty();
    }

}
