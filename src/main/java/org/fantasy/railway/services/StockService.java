package org.fantasy.railway.services;

import com.google.common.collect.Iterables;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Train;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockService {

    List<Train> trains;


    /**
     * loads stock from the given file
     * @param filepath the file to load the stock from
     */
    void loadStock(String filepath) {
        // TODO load stock from file
    }

    /**
     * @param minimumCarriages
     * @return an existing train that is not currently assigned to a service
     */
    Train findAvailableTrain(Service service, Integer minimumCarriages) {
        Optional.ofNullable(service.getTrain()).orElseThrow(() -> new IllegalStateException(String.format("service %s already has a train assigned.")));

        Optional<Train> firstUnallocatedTrain = trains.stream()
                .filter(train -> train.getServices() == null)
                .filter(train -> train.getCarriages().size() >= minimumCarriages)
                .findFirst();

        if (firstUnallocatedTrain.isPresent()) {
            return firstUnallocatedTrain.get();
        }

        Optional<Train> firstAvailableTrain = trains.stream()
                .filter(train -> train.getServices() != null)
                .filter(train -> train.getCarriages().size() >= minimumCarriages)
                .filter(train -> isTrainAvailableAtTime(train, service.getWhen()))
                .findFirst();

        return firstAvailableTrain.orElseThrow(() -> new IllegalStateException(String.format("No unallocated train available with at least %s carriages", minimumCarriages)));

    }

    /**
     *
     * @param train the train to check if available
     * @param whenNeeded the time that the train needs to be free at
     * @return true if the train is available at the needed time
     */
    Boolean isTrainAvailableAtTime(Train train, LocalDateTime whenNeeded) {
        List<Service> services = train.getServices();

        // if train is not allocated any service, then it is considered available
        if (services == null) {
            return true;
        }

        LocalDateTime whenTrainIsFree = services.stream()
                .sorted()
                .skip(services.size() - 1)
                .map(service -> service.finishTime())
                .findFirst()
                .get();

        if (whenTrainIsFree.isAfter(whenNeeded)) {
            return true;
        }

        return false;
    }

    /**
     * creates and adds a new train from unallocated stock
     * @param size the size of the train
     */
    private void addStockFromDepot(TrainSize size) {
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
