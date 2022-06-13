package org.fantasy.railway.services;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.fantasy.railway.model.Line;
import org.fantasy.railway.model.Station;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        // TODO
    }

    /**
     *
     * @param from the starting station of the journey
     * @param to the end station of the journet
     * @return a map of key=stations in correct order with value=distance to next station
     */
    TreeMap<Station, Integer> calculateRoute(Station from, Station to) {
        // TODO
        return null;
    }
}
