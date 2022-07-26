package org.fantasy.railway.service;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.services.NetworkServiceImpl;
import org.fantasy.railway.services.TimetableService;
import org.fantasy.railway.util.Now;
import org.fantasy.railway.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class NetworkServiceImplTest {

    NetworkServiceImpl network;

    @Mock
    TimetableService timetable;

    @BeforeEach
    void setup() {
        network = new NetworkServiceImpl();
        network.setTimetable(timetable);
    }

    @Test
    void shouldLoadNetworkFromFile() {
        network.loadNetwork("test-network.csv");

        assertThat(network.getStation("A")).isPresent();
        assertThat(network.getStation("Z")).isNotPresent();
    }

    @Test
    void shouldThrowExceptionIfStationNotExist() {
        String name = "Test station";
        Station station = Station.builder().name(name).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.getStationOrThrow(name)
        );

        String expected = String.format("Station %s does not exist in the network", name);
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldNotThrowExceptionIfStationExist() {
        String name = "Test station";
        Station station = Station.builder().name(name).build();

        network.getStationOrCreate(name);
        Station actual = network.getStationOrThrow(name);

        assertThat(actual).isEqualTo(station);
    }

    @Test
    void shouldGetStationOrCreate() {
        String name = "Test station";

        Station station = network.getStationOrCreate(name);

        assertThat(network.getItems()).hasSize(1);
        assertThat(network.getStationOrThrow(name)).isEqualTo(station);
        assertThat(network.getItems()).hasSize(1);
        assertThat(network.getStationOrThrow(name)).isEqualTo(station);

        assertThat(station).isNotNull();
        assertThat(station.getName()).isEqualTo(name);
        assertThat(station.getId()).isEqualTo(1);
    }

    @Test
    void shouldGetItems() {
        String name = "test station";

        Station station = network.getStationOrCreate(name);

        assertThat(network.getItems()).contains(station);
    }

    @Test
    void shouldThrowExceptionIfAddConnectionWithStationNotExist() {
        Station station = Station.builder().name("Test station").build();
        Station connection = Station.builder().name("connection").build();
        Map<Station, Integer> connections = new HashMap<>();
        connections.put(connection, 3);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.addConnections(station, connections)
        );

        String expected = String.format("Station %s does not exist in the network", station);
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldAddConnection() {
        String name = "Test station";
        Station connection = Station.builder().name("connection").build();
        Map<Station, Integer> connections = new HashMap<>();
        connections.put(connection, 3);

        Station station = network.getStationOrCreate(name);
        network.addConnections(station, connections);

        assertThat(network.getStation(name)).hasValue(station);
        assertThat(network.getStation(connection.getName())).hasValue(connection);
        assertThat(network.distanceBetweenAdjacent(station, connection)).isEqualTo(3);
    }

    @Test
    void shouldAddStationsFromStringInput() {
        String firstName = "first station";
        String secondName = "second station";
        String thirdName = "third station";

        Integer firstToSecond = 7;
        Integer firstToThird = 9;

        Queue<String> inputs = new LinkedList<>();
        inputs.add(firstName);
        inputs.add(secondName);
        inputs.add(firstToSecond.toString());
        inputs.add(thirdName);
        inputs.add(firstToThird.toString());

        Station station = network.addStation(inputs);
        Station first = network.getStationOrThrow(firstName);
        Station second = network.getStationOrThrow(secondName);
        Station third = network.getStationOrThrow(thirdName);

        assertThat(station.getId()).isEqualTo(1);
        assertThat(station).isEqualTo(first);
        assertThat(network.getItems()).contains(first, second, third);
        assertThat(network.distanceBetweenAdjacent(first, second)).isEqualTo(firstToSecond);
        assertThat(network.distanceBetweenAdjacent(first, third)).isEqualTo(firstToThird);
    }

    @Test
    void shouldRemoveStationIfNoService() {
        Mockito.when(timetable.getServices()).thenReturn(new ArrayList<>());
        String name = "Test station";

        Station station = network.getStationOrCreate(name);
        network.removeStation(station);

        assertThat(network.getStation(station.getName())).isNotPresent();
        Mockito.verify(timetable, Mockito.times(1)).getServices();
    }

    @Test
    void shouldNotRemoveStationIfFutureServiceExists() {
        // create a service that our mock will return...
        Service service = Service.builder().id(1).build();
        service.setRoute(TestUtils.createTestRoute(service));
        service.setName(service.getCurrentName());
        List<Service> services = Arrays.asList(service);

        Mockito.when(timetable.getServices()).thenReturn(services);

        // set the time to be before the time of the service we just created...
        Clock clock = Clock.fixed(Instant.parse("2022-05-10T08:00:00Z"), ZoneOffset.UTC);
        Now.setClock(clock);

        String stationName = service.getRoute().peek().getStation().getName();
        Station station = network.getStationOrCreate(stationName);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.removeStation(station)
        );

        String expected = "There is a service stopping at";
        String actual = exception.getMessage();

        assertThat(actual).startsWith(expected);
        assertThat(network.getStation(station.getName())).isPresent();
        Mockito.verify(timetable, Mockito.times(1)).getServices();
    }
}
