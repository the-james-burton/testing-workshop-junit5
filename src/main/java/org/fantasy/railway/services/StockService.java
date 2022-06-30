package org.fantasy.railway.services;

import org.fantasy.railway.model.Train;

import java.util.List;

public class StockService {

    List<Train> trains;


    /**
     * @param minimumCarriages
     * @return an existing train that is not currently assigned to a service
     */
    Train findAvailableTrain(Integer minimumCarriages) {
        return trains.stream()
                .filter(train -> train.getService() == null)
                .filter(train -> train.getCarriages().size() >= minimumCarriages)
                .findFirst().orElseThrow(() -> new IllegalStateException(String.format("No unallocated train available with at least %s carriages", minimumCarriages)));
    }

    /**
     * creates and adds a new train from unallocated stock
     * @param size the size of the train
     */
    void addStockFromDepot(TrainSize size) {
        switch(size) {
            case LARGE:
                trains.add(Train.ofSizeLarge());
                break;
            case MEDIUM:
                trains.add(Train.ofSizeMedium());
                break;
            case SMALL:
                throw new IllegalArgumentException("Small size trains are not supported");
        }
    }

    /**
     * an indicator of the size of a train
     */
    enum TrainSize {
        LARGE, MEDIUM, SMALL
    }

}
