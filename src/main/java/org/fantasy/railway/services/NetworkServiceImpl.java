package org.fantasy.railway.services;

import com.google.common.base.Preconditions;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import lombok.Getter;
import org.fantasy.railway.model.Journey;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.util.GraphUtils;

import java.util.*;

public class NetworkServiceImpl extends BaseService<Station> implements NetworkService {

    BookingService bookings;
    MutableValueGraph<Station, Integer> network;

    @Getter
    List<Station> stations;

    public NetworkServiceImpl() {
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

    @Override
    public Station newStation(String name) {
        Station station = Station.builder()
                        .name(name)
                        .build();
        stations.add(station);
        return station;
    }

    @Override
    public Station addConnections(Station station, Map<Station, Integer> connections) {
        Preconditions.checkArgument(!stations.contains(station),
            "Station %s does not exist in the network", station);
        stations.add(station);
        network.addNode(station);
        connections.keySet()
                .forEach(s -> network.addNode(s));
        connections
                .forEach((connection, distance) -> network.putEdgeValue(station, connection, distance));
        return station;
    }

    @Override
    public Station addStation(Queue<String> inputs) {
        Preconditions.checkArgument(inputs.size() > 2);

        Station station = stationFromString(inputs.poll());
        Map<Station, Integer> connections = new HashMap<>();

        while (!inputs.isEmpty()) {
            connections.put(
                    stationFromString(inputs.poll()),
                    Integer.parseInt(inputs.poll()));
        }
        return addConnections(station, connections);

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
     * useful for calculating the cost of a ticket and defining a new service
     *
     * @param from the starting station of the journey
     * @param to the end station of the journey
     * @return a Journey with a List of stops in correct order
     */
    public Journey calculateRoute(Station from, Station to) {
        List<Stop> route = new LinkedList<>();
        List<Station> shortestPath = GraphUtils.findShortestPath(network, from, to);

        Preconditions.checkArgument(shortestPath.isEmpty(),
            "No route from %s to %s", from, to);

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

    @Override
    List<Station> getItems() {
        return stations;
    }
}
