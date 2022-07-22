package org.fantasy.railway.services;

import com.google.common.base.Preconditions;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.fantasy.railway.model.Journey;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.util.GraphUtils;
import org.fantasy.railway.util.RailwayUtils;

import java.util.*;
import java.util.stream.Collectors;

public class NetworkServiceImpl extends BaseService<Station> implements NetworkService {

    BookingService bookings;
    MutableValueGraph<Station, Integer> network;


    public NetworkServiceImpl() {
        network = ValueGraphBuilder.undirected().build();
    }

    /**
     * @param name the string to find a Station for
     * @return Station for the given input string
     */
    public Optional<Station> getStation(String name) {
        return network.asGraph().nodes().stream()
                .filter(station -> name.equals(station.getName()))
                .findAny();
    }

    @Override
    public Station getOrCreateStation(String name) {
        return getStation(name).orElseGet(() -> {
            Station station = Station.builder()
                    .name(name)
                    .build();
            network.addNode(station);
            return station;
        });
    }

    @Override
    public Station getStationOrThrow(String name) {
        return getStation(name).orElseThrow(() ->
                new IllegalArgumentException(String.format("Station %s does not exist in the network", name)));
    }

    @Override
    public Station addConnections(Station station, Map<Station, Integer> connections) {
        Preconditions.checkArgument(network.asGraph().nodes().contains(station),
                "Station %s does not exist in the network", station);
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
        Preconditions.checkArgument(inputs.size() % 2 > 0);

        Station station = getOrCreateStation(inputs.poll());
        Map<Station, Integer> connections = new HashMap<>();

        while (!inputs.isEmpty()) {
            connections.put(
                    getOrCreateStation(inputs.poll()),
                    Integer.parseInt(inputs.poll()));
        }
        return addConnections(station, connections);

    }

    @Override
    public void loadNetwork(String filename) {
        RailwayUtils.parseFile(filename).stream()
                .forEach(row -> addStation(row));
    }

    /**
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
     * @param to   the end station of the journey
     * @return a Journey with a List of stops in correct order
     */
    public Journey calculateRoute(Station from, Station to) {
        List<Stop> route = new LinkedList<>();
        List<Station> shortestPath = GraphUtils.findShortestPath(network, from, to);

        Preconditions.checkArgument(!shortestPath.isEmpty(),
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
     * @param from starting station
     * @param to   adjacent station
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
        return network.asGraph().nodes().stream().collect(Collectors.toList());
    }
}
