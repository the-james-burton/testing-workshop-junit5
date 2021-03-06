package org.fantasy.railway.services;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;

import java.util.List;
import java.util.Queue;

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
     * @param filename the filename to load
     */
    void loadServices(String filename);

    /**
     * @param service the id of the service to cancel
     */
    void cancelService(Integer service);

    /**
     * @return a List of all services
     */
    List<Service> getServices();

    void setNetwork(NetworkService network);

    /**
     * @return services that have dispatched
     */
    Queue<Stop> getDispatched();

    /**
     * @param id the id of the Service to lookup
     * @return the Service with the given id
     */
    Service getById(Integer id);

}
