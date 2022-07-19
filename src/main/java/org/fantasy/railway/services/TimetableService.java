package org.fantasy.railway.services;

import org.fantasy.railway.model.Journey;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Train;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;

public interface TimetableService {

    /**
     * creates a new service starting at the given time for the given journey
     *
     * @param startTime when the new service will start
     * @param journey   the route taken by the service
     * @return the service that was created
     */
    Service createNewService(LocalDateTime startTime, Journey journey);

    /**
     * creates a new service from the given inputs
     *
     * @param inputs the inputs to use (datetime (yyy-mm-ddThh:mm:ss), start station, end station)
     * @return the service that was created
     */
    Service createNewService(Queue<String> inputs);

    /**
     * Assigns the given train to the given service
     *
     * @param train   the train to add to the given service
     * @param service the service to add the train to
     */
    void schedule(Train train, Service service);

    /**
     *
     * @return a List of all services
     */
    List<Service> getServices();

    void setStockService(StockServiceImpl stockService);

    void setNetworkService(NetworkService networkService);

    /**
     * Remove the given train from the given service
     *
     * @param train the train to add to the given service
     * @param service the service to add the train to
     */
    void removeTrainFromService(Train train, Service service);

    /**
     *
     * @param id the id of the Service to lookup
     * @return the Service with the given id
     */
    Service getById(Integer id);

    /**
     *
     * @param service the service that we are checking
     * @return true if the service is fully booked
     */
    Boolean isServiceFullyBooked(Service service);
}
