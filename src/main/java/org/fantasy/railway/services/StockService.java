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
        // TODO contruct the train
        switch (trainSize) {
            case SMALL:
                break;
            case MEDIUM:
                break;
            case LARGE:
                break;
            default:
                throw new IllegalArgumentException(String.format("trains of size %s are unsupported", trainSize));
        }
        return null;
    }

    /**
     * @param trainSize
     * @return an existing train that is not currently assigned to a service
     */
    Train findAvailableTrain(TrainSize trainSize) {
        return trains.stream()
                .filter(train -> train.getService() == null)
                .findFirst().orElseThrow(() -> new IllegalStateException(String.format("No unallocation train available of size %s", trainSize)));
    }

    enum TrainSize {
        SMALL, MEDIUM, LARGE
    }

}
