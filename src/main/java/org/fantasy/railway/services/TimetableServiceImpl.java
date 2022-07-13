package org.fantasy.railway.services;

import lombok.Getter;
import lombok.Setter;
import org.fantasy.railway.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableServiceImpl extends BaseService<Service> implements TimetableService {

    public static final Integer MAX_DISTANCE_FOR_SMALL = 20;
    public static final Integer MAX_DISTANCE_FOR_MEDIUM = 20;

    @Setter
    StockServiceImpl stockService;

    @Setter
    NetworkService networkService;

    @Getter
    List<Service> services;

    @Override
    List<Service> getItems() {
        return services;
    }

    /**
     * loads a timetable from the given file
     * @param filepath the file to load the timetable from
     */
    void loadServices(String filepath) {
        // TODO load timetable from file
    }

    @Override
    public void createNewService(LocalDateTime startTime, Journey journey) {
        Stop first = journey.getRoute().get(0);
        services.stream()
                .filter(service -> service.getStartTime().isEqual(startTime))
                .filter(service -> service.getJourney().getRoute().get(0).equals(first))
                .findAny()
                .ifPresent(service -> {
                    throw new IllegalArgumentException(String.format("There is already a service starting from %s at %s", first, startTime));
                });

        Service service = Service.builder()
                .startTime(startTime)
                .journey(journey)
                .build();

        services.add(service);
    }

    @Override
    public void schedule(Train train, Service service) {
        service.setTrain(train);
    }

    /**
     * Remove the given train from the given service
     *
     * @param train the train to add to the given service
     * @param service the service to add the train to
     */
    void removeTrainFromService(Train train, Service service) {
        if (!service.getTrain().equals(train)) {
            throw new IllegalStateException(String.format("Train %s is not assigned to service %s", train, service));
        }
        service.setTrain(null);
        train.getServices().remove(service);
    }

    Service findSuitableService(Ticket ticket) {
        return services.stream()
                .filter(service -> service.getStartTime().isBefore(ticket.getService().getStartTime()))
                .filter(service -> service.getStartTime().toLocalDate().isEqual(ticket.getService().getStartTime().toLocalDate()))
                .filter(service -> service.getJourney().getRoute().containsAll(ticket.getJourney().getRoute()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("unable to find a suitable service for %s", ticket)));
    }

    /**
     * bootstrap method to assign trains to services
     */
    void assignStockToServices() {
        findUnstockedServices()
                .forEach(service -> service.setTrain(stockService.findAvailableTrain(service)));
    }

    /**
     * @return a list of services that do not have a train
     */
    List<Service> findUnstockedServices() {
        return services.stream()
                .filter(service -> service.getTrain() == null)
                .collect(Collectors.toList());
    }



}
