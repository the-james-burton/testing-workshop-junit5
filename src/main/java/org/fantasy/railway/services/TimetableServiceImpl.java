package org.fantasy.railway.services;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.fantasy.railway.model.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TimetableServiceImpl extends BaseService<Service> implements TimetableService {

    public static final Integer MAX_DISTANCE_FOR_SMALL = 20;
    public static final Integer MAX_DISTANCE_FOR_MEDIUM = 20;

    @Setter
    NetworkService networkService;

    @Getter
    List<Service> services;

    ScheduledExecutorService dispatcher = Executors.newSingleThreadScheduledExecutor();


    public TimetableServiceImpl() {
        // TODO implement?
        // Future<Queue<String>> result = dispatcher.schedule(dispatched, 1, TimeUnit.MINUTES);
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
    public Service createNewService(LocalTime startTime, List<Stop> route) {
        Stop first = route.get(0);

        // does a service already exist?
        services.stream()
                .filter(service -> service.getStartTime().equals(startTime))
                .filter(service -> service.getRoute().get(0).equals(first))
                .findAny()
                .ifPresent(service -> {
                    throw new IllegalArgumentException(String.format("There is already a service starting from %s at %s", first, startTime));
                });

        // add the timetable to the journey...
        for (Integer x = 1; x < route.size(); x++) {
            Stop previous = route.get(x - 1);
            Stop current = route.get(x);
            Integer distance = networkService.distanceBetweenAdjacent(previous.getStation(), current.getStation());
            current.setWhen(previous.getWhen().plusMinutes(distance));
        }

        Service service = Service.builder()
                .route(route)
                .build();

        services.add(service);
        return service;
    }

    @Override
    public Service createNewService(Queue<String> inputs) {
        LocalTime startTime = LocalTime.parse(inputs.poll());
        Station start = networkService.getStationOrThrow(inputs.poll());
        Station finish = networkService.getStationOrThrow(inputs.poll());
        List<Stop> route = networkService.calculateRoute(start, finish);

        return createNewService(startTime, route);
    }


}
