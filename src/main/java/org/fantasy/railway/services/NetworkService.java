package org.fantasy.railway.services;

import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public interface NetworkService {

    /**
     * @param name the string to find a Station for
     * @return Station for the given input string
     */
    Optional<Station> getStation(String name);

    /**
     * @param name the name of the new station
     * @return the new station
     */
    Station getStationOrCreate(String name);

    /**
     * @param name the name of the new station
     * @return the new station
     */
    Station getStationOrThrow(String name);

    /**
     * @param station     the station to add to the network
     * @param connections key=station to link to, value=distance in minutes
     * @return the station that was added
     */
    Station addConnections(Station station, Map<Station, Integer> connections);

    /**
     * @param inputs the inputs to use (station name,[connection,distance]...)
     * @return the station that was added
     */
    Station addStation(Queue<String> inputs);

    /**
     * @param filename the filename to load
     */
    void loadNetwork(String filename);

    /**
     * @param station the station to remove from the network
     */
    void removeStation(Station station);

    /**
     * useful for calculating the cost of a ticket and defining a new service
     *
     * @param from the starting station of the journey
     * @param to   the end station of the journey
     * @return a Journey with a List of stops in correct order
     */
    List<Stop> calculateRoute(Station from, Station to);


    /**
     * @param from starting station
     * @param to   adjacent station
     * @return the distance between the two adjacent stations
     */
    Integer distanceBetweenAdjacent(Station from, Station to);

    /**
     *
     * @return a printable version of the contents of the network
     */
    String networkToString();

}
