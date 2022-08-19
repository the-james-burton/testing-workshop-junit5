package org.fantasy.railway.service;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.services.NetworkServiceImpl;
import org.fantasy.railway.services.TimetableService;
import org.fantasy.railway.util.Now;
import org.fantasy.railway.util.TestUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NetworkServiceImplTest {

    @InjectMocks
    NetworkServiceImpl network;

    @Mock
    TimetableService timetable;

    @Test
    void shouldLoadNetworkFromFile() {
        network.loadNetwork("test-network.csv");

        // TODO can assert on all items?
        assertThat(network.getStation("A")).isPresent();
        assertThat(network.getStation("Z")).isNotPresent();
    }

    @Test
    void shouldConvertNetworkToString() {
        network.loadNetwork("test-network.csv");

        String networkToString = network.networkToString();
        assertThat(networkToString).contains("isDirected: false")
                .contains("Station(name=A)")
                .contains("[Station(name=C), Station(name=A)]=2");
    }

    @Test
    void shouldCalculateDistanceBetweenAdjacent() {
        network.loadNetwork("test-network.csv");

        assertThat(network.distanceBetweenAdjacent(
                network.getStationOrThrow("A"),
                network.getStationOrThrow("C")
        )).isEqualTo(2);

    }

    @Test
    void shouldNotDistanceBetweenAdjacentIfNotAdjacent() {
        network.loadNetwork("test-network.csv");

        // keep these calls out of the assertThrows...
        Station a = network.getStationOrThrow("A");
        Station b = network.getStationOrThrow("B");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.distanceBetweenAdjacent(a, b)
        );

        String expected = " are not adjacent";
        String actual = exception.getMessage();

        assertThat(actual).endsWith(expected);
    }

    @Test
    void shouldCalculateRoute() {
        network.loadNetwork("test-network.csv");

        List<Stop> result = network.calculateRoute(
                network.getStationOrThrow("B"),
                network.getStationOrThrow("H"));

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(6);
        assertThat(result).map(stop -> stop.getStation().getName())
                .containsExactly("B", "E", "D", "C", "G", "H");

    }

    @Test
    void shouldNotCalculateRouteIfNotPossible() {
        network.loadNetwork("test-network.csv");

        // add an isolated part of the network...
        Queue<String> inputs = new LinkedList<>(Arrays.asList("Z", "Y", "5"));
        network.addStation(inputs);

        // keep these calls out of the assertThrows...
        Station b = network.getStationOrThrow("B");
        Station z = network.getStationOrThrow("Z");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.calculateRoute(b, z)
        );

        String expected = "No route from ";
        String actual = exception.getMessage();

        assertThat(actual).startsWith(expected);
    }

    @Test
    void shouldThrowExceptionIfStationNotExist() {
        String name = "Test station";

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
    void shouldNotAddStationsIfNotEnoughStringInput() {
        Queue<String> inputs = new LinkedList<>();

        inputs.add("one input should fail");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.addStation(inputs)
        );

        String expected = "There should be at least three inputs";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldNotAddStationsIfWrongNumberOfStringInput() {
        Queue<String> inputs = new LinkedList<>(Arrays.asList("one", "two", "three", "four"));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.addStation(inputs)
        );

        String expected = "There should be an odd number of inputs";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void shouldRemoveStationIfNoService() {
        when(timetable.getServices()).thenReturn(new ArrayList<>());
        String name = "Test station";

        Station station = network.getStationOrCreate(name);
        network.removeStation(station);

        assertThat(network.getStation(station.getName())).isNotPresent();
        verify(timetable, Mockito.times(1)).getServices();
    }

    @Test
    void shouldNotRemoveStationIfFutureServiceExists() {
        // create a service that our mock will return...
        Service service = Service.builder().id(1).build();
        service.setRoute(TestUtils.createTestRoute(service));
        service.setName(service.getCurrentName());
        List<Service> services = Arrays.asList(service);

        when(timetable.getServices()).thenReturn(services);

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
        verify(timetable, Mockito.times(1)).getServices();
    }

    @TestFactory
    Stream<DynamicTest> shouldAlwaysCalculateRoute() {

        // TODO dynamic test exercise

        network.loadNetwork("test-network.csv");

        return IntStream
                .iterate(0, n -> n + 1)
                .limit(10)
                .mapToObj(n -> {
                    List<Station> stations = network.getItems();
                    Collections.shuffle(stations);
                    Station from = stations.get(0);
                    Station to = stations.get(1);
                    return dynamicTest(
                            String.format("%s -> %s", from.getName(), to.getName()),
                            () -> assertThat(network.calculateRoute(from, to))
                                    .isNotNull());
                });
    }

}
