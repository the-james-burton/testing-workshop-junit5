package org.fantasy.railway.services;

import org.fantasy.railway.model.Train;

import java.util.List;

public interface StockService {
    /**
     * creates and adds a new train from unallocated stock
     *
     * @param numberOfCarriages the size of the train needed
     * @return the train that was added
     */
    Train addStockFromDepot(Integer numberOfCarriages);

    /**
     * withdraws a train from all services
     *
     * @param train the Id of the train to withdraw
     * @return the train that was withdrawn
     */
    Train withdrawTrain(Train train);

    /**
     * removes a train from the stock list
     *
     * @param train the Id of the train needed
     * @return the train that was removed
     */
    Train decommissionTrain(Train train);

    /**
     * @return a list of all trains
     */
    List<Train> getTrains();

    /**
     * @param id the id of the Train to lookup
     * @return the Train with the given id
     */
    Train getById(Integer id);
}
