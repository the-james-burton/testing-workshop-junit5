package org.fantasy.railway.services;

import com.google.common.graph.Network;
import org.fantasy.railway.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableService {

    public static final Integer MAX_DISTANCE_FOR_SMALL = 20;
    public static final Integer MAX_DISTANCE_FOR_MEDIUM = 20;
    StockService stockService;
    NetworkService networkService;
    List<Service> services;
    List<Station> stations;

    /**
     * loads a timetable from the given file
     * @param filepath the file to load the timetable from
     */
    void loadServices(String filepath) {
        // TODO load timetable from file
    }

    Service findSuitableService(Ticket ticket) {
        return services.stream()
                .filter(service -> service.getStartTime().isBefore(ticket.getService().getStartTime()))
                .filter(service -> service.getStartTime().toLocalDate().isEqual(ticket.getService().getStartTime().toLocalDate()))
                .filter(service -> service.getJourney().getRoute().containsAll(ticket.getJourney().getRoute()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("unable to find a suitable service for ")));
    }

    /**
     * bootstrap method to assign trains to services
     */
    void assignStockToServices() {
        findUnstockedServices().stream()
                .forEach(service -> service.setTrain(findTrainForService(service)));
    }

    /**
     *
     * @param service the service to find a train for
     * @return a train suitable for the given service
     */
    Train findTrainForService(Service service) {
        Integer minimumCarriages = Train.MEDIUM_TRAIN_CARRIAGES;
        if (service.getJourney().totalTime() > MAX_DISTANCE_FOR_SMALL) {
            minimumCarriages = Train.MEDIUM_TRAIN_CARRIAGES;
        } else if (service.getJourney().totalTime() > MAX_DISTANCE_FOR_MEDIUM) {
            minimumCarriages = Train.LARGE_TRAIN_CARRIAGES;
        }
        Train train = stockService.findAvailableTrain(service, minimumCarriages);
        return train;
    }

    /**
     * @return a list of services that do not have a train
     */
    List<Service> findUnstockedServices() {
        return services.stream()
                .filter(service -> service.getTrain() == null)
                .collect(Collectors.toList());
    }

    /**
     * Assigns the given train to the given service
     *
     * @param train the train to add to the given service
     * @param service the service to add the train to
     */
    void schedule(Train train, Service service) {
        service.setTrain(train);
    }
}
