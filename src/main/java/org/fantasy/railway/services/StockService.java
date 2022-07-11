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
     * @param service the Service to find a suitable train for
     * @return an existing train that is not currently assigned to a service
     */
    Train findAvailableTrain(Service service) {
        Optional.ofNullable(service.getTrain()).ifPresent(train ->
                new IllegalStateException(String.format("service %s already has train %s assigned.", service, train)));

        Optional<Train> firstUnallocatedTrain = trains.stream()
                .filter(train -> train.getServices() == null)
                .filter(train -> train.getType().maxDistance >= service.getJourney().totalTime())
                .findFirst();

        if (firstUnallocatedTrain.isPresent()) {
            return firstUnallocatedTrain.get();
        }

        Optional<Train> firstAvailableTrain = trains.stream()
                .filter(train -> train.getServices() != null)
                .filter(train -> train.getType().maxDistance >= service.getJourney().totalTime())
                .filter(train -> isTrainAvailableAtTime(train, service.getStartTime()))
                .findFirst();

        return firstAvailableTrain.orElseThrow(() ->
                new IllegalStateException("No train available suitable for the service"));

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
                .orElseThrow(() -> new IllegalStateException(String.format("train %s is never free!", train)));

        if (whenTrainIsFree.isAfter(whenNeeded)) {
            return true;
        }

        return false;
    }

    /**
     * creates and adds a new train from unallocated stock
     * @param type the type of the train needed
     */
    private void addStockFromDepot(Train.Type type) {
        trains.add(Train.ofType(type));
    }

}
