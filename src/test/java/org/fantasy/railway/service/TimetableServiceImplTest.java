package org.fantasy.railway.service;

import org.assertj.core.api.Assertions;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.TimetableServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.createTestRoute;
import static org.fantasy.railway.util.TestUtils.firstStop;
import static org.fantasy.railway.util.TestUtils.secondStop;
import static org.fantasy.railway.util.TestUtils.thirdStop;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimetableServiceImplTest {

    TimetableServiceImpl timetable;

    @Mock
    NetworkService network;

    @BeforeEach
    void setup() {
        timetable = new TimetableServiceImpl();
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

}
