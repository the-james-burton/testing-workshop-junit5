package org.fantasy.railway.services;

import com.google.common.base.Preconditions;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.util.GraphUtils;
import org.fantasy.railway.util.RailwayUtils;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class NetworkServiceImpl extends BaseService<Station> implements NetworkService {

    TimetableService timetable;
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
    public Station getStationOrCreate(String name) {
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

        Station station = getStationOrCreate(inputs.poll());
        Map<Station, Integer> connections = new HashMap<>();

        while (!inputs.isEmpty()) {
            connections.put(
                    getStationOrCreate(inputs.poll()),
                    Integer.parseInt(inputs.poll()));
        }
        return addConnections(station, connections);

    }

    @Override
    public void loadNetwork(String filename) {
        RailwayUtils.parseFile(filename)
                .forEach(this::addStation);
    }

    /**
     * @param station the station to remove from the network
     */
    public void removeStation(Station station) {
        // do not remove the station from the network if there are any services that stop at it...
        timetable.getServices().stream()
                .filter(service -> service.getFinishTime().isAfter(LocalTime.now()))
                .flatMap(service -> service.getRoute().stream())
                .map(Stop::getStation)
                .filter(stop -> stop.getName().equals(station.getName()))
                .findAny()
                .ifPresent(stop -> {
                    throw new IllegalArgumentException(String.format("There is a service that stops at %s", station));
                });

        network.removeNode(station);
    }

    public List<Stop> calculateRoute(Station from, Station to) {
        List<Stop> route = new LinkedList<>();
        List<Station> shortestPath = GraphUtils.findShortestPath(network, from, to);

        Preconditions.checkArgument(!shortestPath.isEmpty(),
                "No route from %s to %s", from, to);

        // ths first station will have value of zero since it is the start...
        route.add(Stop.builder()
                .station(shortestPath.get(0))
                .when(LocalTime.parse("00:00:00"))
                .build());

        for (int stop = 1; shortestPath.size() - 1 > stop; stop++) {
            Station previous = shortestPath.get(stop - 1);
            Station current = shortestPath.get(stop);
            route.add(Stop.builder()
                    .station(current)
                    .when(route.get(stop).getWhen().plusMinutes(distanceBetweenAdjacent(previous, current)))
                    .build());
        }

        return route;
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
