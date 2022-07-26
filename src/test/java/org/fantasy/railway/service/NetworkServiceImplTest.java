package org.fantasy.railway.service;

import org.fantasy.railway.model.Station;
import org.fantasy.railway.services.NetworkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NetworkServiceImplTest {

    NetworkServiceImpl network;

    @BeforeEach
    void setup() {
        network = new NetworkServiceImpl();
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
        String first = "first station";
        String second = "second station";
        String third = "third station";

        Integer firstToSecond = 7;
        Integer secondToThird = 9;

        Queue<String> inputs = new LinkedList<>();
        inputs.add(first);
        inputs.add(second);
        inputs.add(firstToSecond.toString());
        inputs.add(third);
        inputs.add(secondToThird.toString());

        Station station = network.addStation(inputs);

        assertThat(station.getId()).isEqualTo(1);
        assertThat(station.getName()).isEqualTo(first);
        assertThat(network.getItems()).contains(
                network.getStationOrThrow(first),
                network.getStationOrThrow(second),
                network.getStationOrThrow(third));

    }

}
