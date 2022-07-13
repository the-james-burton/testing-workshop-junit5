package org.fantasy.railway.services;

import org.fantasy.railway.model.Train;

import java.util.List;

public interface StockService {
    /**
     * creates and adds a new train from unallocated stock
     *
     * @param type the type of the train needed
     */
    void addStockFromDepot(Train.Type type);

    /**
     * removes a train from the stock list
     *
     * @param trainId the Id of the train needed
     */
    void decommissionTrain(Integer trainId);

    /**
     *
     * @return a list of all trains
     */
    List<Train> getTrains();

    /**
     *
     * @param id the id of the Train to lookup
     * @return the Train with the given id
     */
    Train getById(Integer id);
}
