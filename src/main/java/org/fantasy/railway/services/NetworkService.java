package org.fantasy.railway.services;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.fantasy.railway.model.Line;
import org.fantasy.railway.model.Station;

import java.util.List;
import java.util.Map;

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

}
