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
        // TODO EXERCISE 5
    }

    @Test
    void shouldConvertNetworkToString() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldCalculateDistanceBetweenAdjacent() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldNotDistanceBetweenAdjacentIfNotAdjacent() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldCalculateRoute() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldNotCalculateRouteIfNotPossible() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldThrowExceptionIfStationNotExist() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldNotThrowExceptionIfStationExist() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldGetStationOrCreate() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldGetItems() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldThrowExceptionIfAddConnectionWithStationNotExist() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldAddConnection() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldAddStationsFromStringInput() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldNotAddStationsIfNotEnoughStringInput() {
        // TODO EXERCISE 5
    }

    @Test
    void shouldNotAddStationsIfWrongNumberOfStringInput() {
        // TODO EXERCISE 5
    }

}
