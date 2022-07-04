package org.fantasy.railway.services;

import com.google.common.collect.Iterables;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.fantasy.railway.model.*;
import org.fantasy.railway.util.GraphUtils;

import java.util.*;

public class NetworkService {

    MutableValueGraph<Station, Integer> network;
    List<Line> lines;

    private NetworkService() {
        network = ValueGraphBuilder.undirected().build();
    }

    /**
     * @param station     the station to add to the network
     * @param connections key=station to link to, value=distance in minutes
     */
    void addStation(Station station, Line line, Map<Station, Integer> connections) {
        network.addNode(station);
        connections.keySet()
                .forEach(s -> network.addNode(s));
        connections
                .forEach((connection, distance) -> network.putEdgeValue(station, connection, distance));
        line.getStations().add(station);
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

        List<Station> stations = GraphUtils.findShortestPath(network, from, to);

        if (stations == null || stations.size() == 0) {
            throw new IllegalArgumentException(String.format("No route from %s to %s", from, to));
        }

        // ths first station will have value of zero since it is the start...
        route.add(Stop.builder()
                .station(stations.get(0))
                .minutes(0)
                .build());

        for (int stop = 1; stations.size() - 1 > stop; stop++) {
            Station current = stations.get(stop);
            Station next = stations.get(stop + 1);
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



}
