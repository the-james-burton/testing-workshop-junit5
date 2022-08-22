package org.fantasy.railway.service;

import org.fantasy.railway.services.NetworkServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NetworkServiceImplTest {

    NetworkServiceImpl network;

    @BeforeEach
    void setup() {
        network = new NetworkServiceImpl();
    }

    @Test
    void shouldLoadNetworkFromFile() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldConvertNetworkToString() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldCalculateDistanceBetweenAdjacent() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldNotDistanceBetweenAdjacentIfNotAdjacent() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldCalculateRoute() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldNotCalculateRouteIfNotPossible() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldThrowExceptionIfStationNotExist() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldNotThrowExceptionIfStationExist() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldGetStationOrCreate() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldGetItems() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldThrowExceptionIfAddConnectionWithStationNotExist() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldAddConnection() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldAddStationsFromStringInput() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldNotAddStationsIfNotEnoughStringInput() {
        // TODO EXERCISE 4
    }

    @Test
    void shouldNotAddStationsIfWrongNumberOfStringInput() {
        // TODO EXERCISE 4
    }

}
