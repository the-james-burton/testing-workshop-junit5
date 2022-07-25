package org.fantasy.railway.services;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Stop;

import java.time.LocalTime;
import java.util.List;
import java.util.Queue;

public interface TimetableService {

    /**
     * creates a new service starting at the given time for the given journey
     *
     * @param startTime when the new service will start
     * @param route     the route taken by the service
     * @return the service that was created
     */
    Service createNewService(LocalTime startTime, List<Stop> route);

    /**
     * creates a new service from the given inputs
     *
     * @param inputs the inputs to use (datetime (yyy-mm-ddThh:mm:ss), start station, end station)
     * @return the service that was created
     */
    Service createNewService(Queue<String> inputs);

    /**
     * @return a List of all services
     */
    List<Service> getServices();

    void setNetworkService(NetworkService networkService);


    /**
     * @param id the id of the Service to lookup
     * @return the Service with the given id
     */
    Service getById(Integer id);

}
