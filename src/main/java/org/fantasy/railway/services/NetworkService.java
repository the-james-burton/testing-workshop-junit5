package org.fantasy.railway.services;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import lombok.Getter;
import org.fantasy.railway.model.*;
import org.fantasy.railway.util.GraphUtils;

import java.util.*;

public class NetworkService {

    BookingService bookings;
    MutableValueGraph<Station, Integer> network;

    @Getter
    List<Station> stations;

    public NetworkService() {
        network = ValueGraphBuilder.undirected().build();
    }

    /**
     *
     * @param stationName the string to find a Station for
     * @return Station for the given input string
     */
    public Station stationFromString(String stationName) {
        return stations.stream()
                .filter(station -> stationName.equals(station.getName()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Station %s does not exist in the network", stationName)));
    }

    /**
     * @param station     the station to add to the network
     * @param connections key=station to link to, value=distance in minutes
     */
    public void addStation(Station station, Map<Station, Integer> connections) {
        stations.add(station);
        network.addNode(station);
        connections.keySet()
                .forEach(s -> network.addNode(s));
        connections
                .forEach((connection, distance) -> network.putEdgeValue(station, connection, distance));
    }

    /**
     *
     * @param station the station to remove from the network
     */
    public void removeStation(Station station) {
        // do not remove the station from the network if there are any tickets that stop at it...
        bookings.getTickets().stream()
                .flatMap(ticket -> ticket.getJourney().getRoute().stream())
                .map(Stop::getStation)
                .filter(stop -> stop.getName().equals(station.getName()))
                .findAny()
                .ifPresent(stop -> {
                    throw new IllegalArgumentException(String.format("There is a ticket sold that has a journey stopping at %s", station));
                });

        network.removeNode(station);
    }

    /**
     * useful for calculating the cost of a ticket
     *
     * @param from the starting station of the journey
     * @param to the end station of the journey
     * @return a Journey with a List of stops in correct order
     */
    Journey calculateRoute(Station from, Station to) {
        List<Stop> route = new LinkedList<>();
        List<Station> shortestPath = GraphUtils.findShortestPath(network, from, to);

        if (shortestPath == null || shortestPath.size() == 0) {
            throw new IllegalArgumentException(String.format("No route from %s to %s", from, to));
        }

        // ths first station will have value of zero since it is the start...
        route.add(Stop.builder()
                .station(shortestPath.get(0))
                .minutes(0)
                .build());

        for (int stop = 1; shortestPath.size() - 1 > stop; stop++) {
            Station current = shortestPath.get(stop);
            Station next = shortestPath.get(stop + 1);
            route.add(Stop.builder()
                .station(current)
                .minutes(distanceBetweenAdjacent(current, next))
                .build());
        }

        return Journey.builder().route(route).build();
    }


        /**
         *
         * @param from starting station
         * @param to adjacent station
         * @return the distance between the two adjacent stations
         */
    public Integer distanceBetweenAdjacent(Station from, Station to) {
        return network.edgeValue(from, to)
                .orElseThrow(() -> new IllegalStateException(String.format("Stations %s and %s are not adjacent", from, to)));
    }

    public String networkToString() {
        // TODO this almost certainly will not actually be useful...
        return network.toString();
    }

}
