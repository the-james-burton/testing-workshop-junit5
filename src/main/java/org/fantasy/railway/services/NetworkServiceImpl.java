package org.fantasy.railway.services;

import com.google.common.base.Preconditions;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import lombok.Setter;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.util.GraphUtils;
import org.fantasy.railway.util.RailwayUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

@SuppressWarnings("UnstableApiUsage")
public class NetworkServiceImpl extends BaseService<Station> implements NetworkService {

    @Setter
    TimetableService timetable;

    @Setter
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
                    .id(nextId())
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
                .forEach(network::addNode);
        connections
                .forEach((connection, distance) -> network.putEdgeValue(station, connection, distance));
        return station;
    }

    @Override
    public Station addStation(Queue<String> inputs) {
        Preconditions.checkArgument(inputs.size() > 2, "There should be at least three inputs");
        Preconditions.checkArgument(inputs.size() % 2 > 0, "There should be an odd number of inputs");

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

    public List<Stop> calculateRoute(Station from, Station to) {
        List<Stop> route = new LinkedList<>();
        List<Station> shortestPath = GraphUtils.findShortestPath(network, from, to);

        Preconditions.checkArgument(!shortestPath.isEmpty(),
                "No route from %s to %s", from, to);

        // initialize the route...
        shortestPath
                .forEach(station -> route.add(Stop.builder()
                        .station(station)
                        .build()));

        //
        for (int stop = 1; route.size() > stop; stop++) {
            Stop previous = route.get(stop - 1);
            Stop current = route.get(stop);
            current.setWhen(previous.getWhen().plusMinutes(
                    distanceBetweenAdjacent(previous.getStation(), current.getStation())
            ));
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
                .orElseThrow(() -> new IllegalArgumentException(String.format("Stations %s and %s are not adjacent", from, to)));
    }

    @Override
    public String networkToString() {
        return network.toString();
    }

    @Override
    public List<Station> getItems() {
        return network.asGraph().nodes().stream().collect(Collectors.toList());
    }
}
