package org.fantasy.railway.services;

import org.fantasy.railway.model.*;

import java.util.*;

public interface NetworkService {

    /**
     *
     * @param stationName the string to find a Station for
     * @return Station for the given input string
     */
    Station stationFromString(String stationName);

    /**
     * @param station     the station to add to the network
     * @param connections key=station to link to, value=distance in minutes
     */
    void addStation(Station station, Map<Station, Integer> connections);

    /**
     *
     * @param station the station to remove from the network
     */
    void removeStation(Station station);

    /**
     * useful for calculating the cost of a ticket and defining a new service
     *
     * @param from the starting station of the journey
     * @param to the end station of the journey
     * @return a Journey with a List of stops in correct order
     */
    Journey calculateRoute(Station from, Station to);


        /**
         *
         * @param from starting station
         * @param to adjacent station
         * @return the distance between the two adjacent stations
         */
    Integer distanceBetweenAdjacent(Station from, Station to);

    String networkToString();

}
