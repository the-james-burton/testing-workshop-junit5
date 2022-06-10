package org.fantasy.railway.services;

import org.fantasy.railway.model.Train;

import java.util.List;

public class StockService {

    List<Train> trains;

    /**
     * @param trainSize the size of the required train
     * @return a newly created train of the given size
     */
    Train createTrain(TrainSize trainSize) {
        // TODO
        return null;
    }

    /**
     * @param trainSize
     * @return an existing train that is not currently assigned to a service
     */
    Train findAvailableTrain(TrainSize trainSize) {
        // TODO
        return null;
    }

    enum TrainSize {
        SMALL, MEDIUM, LARGE
    }

}
