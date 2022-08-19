package org.fantasy.railway.services;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;

import java.util.List;
import java.util.Queue;

/**
 * Defines a way to manage train services running on the railway network
 */
public interface TimetableService {

    /**
     * creates new services with the given frequency starting at the given time for the given journey
     *
     * @param frequency how often the service will run (minutes)
     * @param start     the starting station
     * @param finish    the finishing station
     * @return the service that was created
     */
    List<Service> createNewServices(Integer frequency, Station start, Station finish);

    /**
     * creates a new service from the given inputs
     *
     * @param inputs the inputs to use (datetime (yyy-mm-ddThh:mm:ss), start station, end station)
     * @return the service that was created
     */
    List<Service> createNewServices(Queue<String> inputs);

    /**
     * @param filename the filename to load services from
     */
    void loadServices(String filename);

    /**
     * @return a List of all services
     */
    List<Service> getServices();

    /**
     * @return services that have dispatched
     */
    Queue<Stop> getDispatched();

    void setNetwork(NetworkService network);

    /**
     * shutdown any timer tasks running the dispatching so the system can exit cleanly
     */
    void shutdown();

}
