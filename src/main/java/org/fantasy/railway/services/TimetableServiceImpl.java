package org.fantasy.railway.services;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.fantasy.railway.model.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
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

    ScheduledExecutorService dispatcher = Executors.newSingleThreadScheduledExecutor();

    Callable<Queue<String>> dispatched = () -> {
        Queue<String> result = new LinkedList<>();
        return services.stream()
                .filter(service -> service.getStartTime().isBefore(LocalDateTime.now()))
                .filter(service -> service.getFinishTime().isAfter(LocalDateTime.now()))
                .map(service ->
                    Dispatching.builder()
                            .service(service)
                            .stop(service.getJourney().getCumulativeTimeRoute().stream()
                                .filter(stop -> service.getFinishTime().plusMinutes(stop.getMinutes()).isAfter(LocalDateTime.now()))
                                .findFirst()
                                .orElse(Stop.builder().build()))
                            .build())
                .map(dispatching -> String.format("Service %s next stop %s",
                        dispatching.getService(), dispatching.getStop()))
                .collect(Collectors.toCollection(LinkedList::new));
    };


    public TimetableServiceImpl() {
        Future<Queue<String>> result = dispatcher.schedule(dispatched, 1, TimeUnit.MINUTES);
    }

    @Override
    List<Service> getItems() {
        return services;
    }

    /**
     * loads a timetable from the given file
     *
     * @param filepath the file to load the timetable from
     */
    void loadServices(String filepath) {
        // TODO load timetable from file
    }

    @Override
    public Service createNewService(LocalDateTime startTime, Journey journey) {
        Stop first = journey.getRoute().get(0);

        // does a service already exist?
        services.stream()
                .filter(service -> service.getStartTime().isEqual(startTime))
                .filter(service -> service.getJourney().getRoute().get(0).equals(first))
                .findAny()
                .ifPresent(service -> {
                    throw new IllegalArgumentException(String.format("There is already a service starting from %s at %s", first, startTime));
                });

        first.setWhen(startTime);

        // add the timetable to the journey...
        for (Integer x = 1; x < journey.getRoute().size(); x++) {
            Stop previous = journey.getRoute().get(x - 1);
            Stop current = journey.getRoute().get(x);
            current.setWhen(previous.getWhen().plusMinutes(current.getMinutes()));
        }

        Service service = Service.builder()
                .journey(journey)
                .build();

        services.add(service);
        return service;
    }

    @Override
    public Service createNewService(Queue<String> inputs) {
        LocalDateTime startTime = LocalDateTime.parse(inputs.poll());
        Station start = networkService.getStationOrThrow(inputs.poll());
        Station finish = networkService.getStationOrThrow(inputs.poll());
        Journey journey = networkService.calculateRoute(start, finish);

        return createNewService(startTime, journey);
    }

    @Override
    public void schedule(Train train, Service service) {
        service.setTrain(train);
    }

    @Override
    public void removeTrainFromService(Train train, Service service) {
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
                .filter(service -> !isServiceFullyBooked(service))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("unable to find a suitable service for %s", ticket)));
    }

    /**
     * automatically assign trains to services
     */
    @Override
    public void autoSchedule() {
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

    @Override
    public Boolean isServiceFullyBooked(Service service) {
        Preconditions.checkArgument(service.getTrain() == null,
                "Service %s is not assigned a train.", service);

        return service.getTrain().getCarriages().stream()
                .flatMap(carriage -> carriage.getSeats().stream())
                .allMatch(seat -> seat.isBookedFor(service));
    }

}
