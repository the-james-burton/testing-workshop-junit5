package org.fantasy.railway.service;

import com.google.common.collect.Sets;
import com.google.common.graph.MutableValueGraph;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.services.NetworkServiceImpl;
import org.fantasy.railway.services.TimetableService;
import org.fantasy.railway.util.GraphUtils;
import org.fantasy.railway.util.RailwayUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NetworkServiceImplTest {

    static final Station one = Station.builder().id(1).name("one").build();
    static final Station two = Station.builder().id(2).name("two").build();
    static final Station three = Station.builder().id(3).name("three").build();

    @Spy
    @InjectMocks
    NetworkServiceImpl network;

    @Mock
    TimetableService timetable;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    MutableValueGraph<Station, Integer> networkGraph;

    @Test
    void shouldLoadNetworkFromFile() {
        String filename = "not-real-file.csv";
        Queue<String> row = new LinkedList<>();
        Queue<Queue<String>> results = new LinkedList<>();
        row.addAll(Arrays.asList("one", "two", "three"));
        results.add(row);

        try (MockedStatic<RailwayUtils> utils = Mockito.mockStatic(RailwayUtils.class)) {

            utils.when(() -> RailwayUtils.parseFile(filename)).thenReturn(results);

            // the return value isn't important as it is not used in the method under test...
            doReturn(one).when(network).addStation(row);

            // execute the method under test...
            network.loadNetwork(filename);

            // verify the mocked static method was called...
            utils.verify(() -> RailwayUtils.parseFile(filename),
                    times(1)
            );

            // verify that the forEach made the expected calls...
            verify(network, times(results.size())).addStation(row);
        }

    }

    @Test
    void shouldGetStation() {
        Station one = Station.builder().name("one").build();
        Station two = Station.builder().name("two").build();

        when(networkGraph.asGraph().nodes()).thenReturn(Sets.newHashSet(one, two));

        assertThat(network.getStation(one.getName())).contains(one);
    }

    @Test
    void shouldConvertNetworkToString() {
        String expected = "to string return";
        when(networkGraph.toString()).thenReturn(expected);

        assertThat(network.networkToString()).isEqualTo(expected);
    }

    @Test
    void shouldCalculateDistanceBetweenAdjacent() {
        Integer expected = 7;

        when(networkGraph.edgeValue(one, two)).thenReturn(Optional.of(expected));

        assertThat(network.distanceBetweenAdjacent(one, two)).isEqualTo(expected);

    }

    @Test
    void shouldNotDistanceBetweenAdjacentIfNotAdjacent() {
        when(networkGraph.edgeValue(one, two)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.distanceBetweenAdjacent(one, two)
        );

        String expected = " are not adjacent";
        String actual = exception.getMessage();

        assertThat(actual).endsWith(expected);
    }

    @Test
    void shouldCalculateRoute() {

        // expected shortest path for our mock to return...
        List<Station> shortestPath = Arrays.asList(one, two, three);

        // cannot use "when" in partial mocks, use doReturn instead...
        doReturn(2).when(network).distanceBetweenAdjacent(one, two);
        doReturn(3).when(network).distanceBetweenAdjacent(two, three);

        try (MockedStatic<GraphUtils> main = Mockito.mockStatic(GraphUtils.class)) {

            // mock the external static function being used...
            main.when(() -> GraphUtils.findShortestPath(networkGraph, one, three)).thenReturn(shortestPath);

            // execute the method under test...
            List<Stop> result = network.calculateRoute(one, three);

            // verify the mocked static method was called...
            main.verify(() -> GraphUtils.findShortestPath(networkGraph, one, three), times(1));

            assertThat(result).hasSize(3);
            assertThat(Duration.between(
                    result.get(0).getWhen(),
                    result.get(2).getWhen()
            ).toMinutes()).isEqualTo(5);
            assertThat(result).map(stop -> stop.getStation().getName())
                    .containsExactly("one", "two", "three");
        }

    }

    @Test
    void shouldNotCalculateRouteIfNotPossible() {
         try (MockedStatic<GraphUtils> main = Mockito.mockStatic(GraphUtils.class)) {

            // mock the external static function being used...
            main.when(() -> GraphUtils.findShortestPath(networkGraph, one, two)).thenReturn(new ArrayList<>());

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    network.calculateRoute(one, two)
            );

            String expected = "No route from ";
            String actual = exception.getMessage();

            assertThat(actual).startsWith(expected);
        }

    }

    @Test
    void shouldThrowExceptionIfStationNotExist() {
        when(networkGraph.asGraph().nodes()).thenReturn(Sets.newHashSet(one, two));
        String name = three.getName();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.getStationOrThrow(name)
        );

        String expected = String.format("Station %s does not exist in the network", three.getName());
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetStationIfStationExist() {
        when(networkGraph.asGraph().nodes()).thenReturn(Sets.newHashSet(one, two));

        Station actual = network.getStationOrCreate(one.getName());

        assertThat(actual).isEqualTo(one);
        verify(networkGraph, times(0)).addNode(any());
    }

    @Test
    void shouldCreateStationIfNotExists() {
        String newStation = "three";

        when(networkGraph.asGraph().nodes()).thenReturn(Sets.newHashSet(one, two));
        doReturn(Arrays.asList(one, two)).when(network).getItems();

        Station actual = network.getStationOrCreate(newStation);

        assertThat(actual.getName()).isEqualTo(newStation);
        assertThat(actual.getId()).isEqualTo(3);

        // this is an important verification...
        verify(networkGraph, times(1)).addNode(any());

    }

    @Test
    void shouldGetItems() {
        when(networkGraph.asGraph().nodes()).thenReturn(Sets.newHashSet(one, two));

        assertThat(network.getItems()).containsExactlyInAnyOrder(one, two);
    }

    @Test
    void shouldThrowExceptionIfAddConnectionWithStationNotExist() {
        when(networkGraph.asGraph().nodes()).thenReturn(Sets.newHashSet(one, two));

        Map<Station, Integer> connections = new HashMap<>();
        connections.put(two, 3);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                network.addConnections(three, connections)
        );

        String expected = String.format("Station %s does not exist in the network", three);
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldAddConnection() {
        when(networkGraph.asGraph().nodes()).thenReturn(Sets.newHashSet(one, two, three));

        Map<Station, Integer> connections = new HashMap<>();
        connections.put(two, 3);

        network.addConnections(three, connections);

        assertThat(network.getStation(three.getName())).hasValue(three);
        verify(networkGraph, times(1)).addNode(three);
        verify(networkGraph, times(1)).putEdgeValue(three, two, 3);
    }

    @Test
    void shouldAddStationsFromStringInput() {
        Queue<String> inputs = new LinkedList<>();
        inputs.add(one.getName());
        inputs.add(two.getName());
        inputs.add("7");
        inputs.add(three.getName());
        inputs.add("9");

        doReturn(one).when(network).getStationOrCreate(one.getName());
        doReturn(two).when(network).getStationOrCreate(two.getName());
        doReturn(three).when(network).getStationOrCreate(three.getName());
        doReturn(one).when(network).addConnections(eq(one), any());

        Station station = network.addStation(inputs);
        verify(network, times(1)).getStationOrCreate(one.getName());
        verify(network, times(1)).getStationOrCreate(two.getName());
        verify(network, times(1)).getStationOrCreate(three.getName());
        verify(network, times(1)).addConnections(eq(one), any());

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

}
