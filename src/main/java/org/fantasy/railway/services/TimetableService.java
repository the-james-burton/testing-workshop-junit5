package org.fantasy.railway.services;

import org.fantasy.railway.model.Journey;
import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Train;

import java.time.LocalDateTime;

public interface TimetableService {
    /**
     * creates a new service starting at the given time for the given journey
     *
     * @param startTime when the new service will start
     * @param journey   the route taken by the service
     */
    void createNewService(LocalDateTime startTime, Journey journey);

    /**
     * Assigns the given train to the given service
     *
     * @param train   the train to add to the given service
     * @param service the service to add the train to
     */
    void schedule(Train train, Service service);

    java.util.List<Service> getServices();

    void setStockService(StockServiceImpl stockService);

    void setNetworkService(NetworkService networkService);

    /**
     *
     * @param id the id of the Service to lookup
     * @return the Service with the given id
     */
    Service getById(Integer id);
}
