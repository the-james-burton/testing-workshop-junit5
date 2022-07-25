package org.fantasy.railway.services;

import lombok.Getter;
import lombok.Setter;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.util.RailwayUtils;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimetableServiceImpl extends BaseService<Service> implements TimetableService {

    public static final Integer MAX_DISTANCE_FOR_SMALL = 20;
    public static final Integer MAX_DISTANCE_FOR_MEDIUM = 20;

    @Setter
    NetworkService networkService;

    @Getter
    List<Service> services = new ArrayList<>();

    ScheduledExecutorService dispatcher = Executors.newSingleThreadScheduledExecutor();

    Queue<Stop> dispatched = new LinkedList<>();

    public TimetableServiceImpl() {
        dispatcher.scheduleAtFixedRate(this::dispatch, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public List<Service> getItems() {
        return services;
    }

    /**
     * loads a timetable from the given file
     *
     * @param filename the file to load the timetable from
     */
    @Override
    public void loadServices(String filename) {
        RailwayUtils.parseFile(filename)
                .forEach(this::createNewServices);
        dispatch();
        dispatched.clear();
    }

    @Override
    public void cancelService(Integer serviceId) {
        services.remove(services.stream()
                .filter(service -> serviceId.equals(service.getId()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("Service with id %s does not exist", serviceId))
                ));
    }

    private void dispatch() {
        // TODO remove this
        System.out.print("dispatching... "); // TODO remove this

        for (Service service : services) {
            Queue<Stop> route = service.getRoute();
            if (route.isEmpty()) continue;
            do {
                Stop current = route.peek();
                if (current.getWhen().isAfter(LocalTime.now())) break;
                dispatched.add(route.poll());
            } while (!route.isEmpty());
        }

        // TODO remove this
        System.out.format("Total %s stops visited%n%n", dispatched.size()); // TODO remove this
    }


    @Override
    public Queue<Stop> getDispatched() {
        return dispatched;
    }

    @Override
    public List<Service> createNewServices(Integer frequency, List<Stop> route) {
        Stop first = route.get(0);
        first.setWhen(first.getWhen().plusMinutes(frequency));

        // does a service already exist with the same route?
        services.stream()
                .filter(service -> service.getRoute().containsAll(route))
                .findAny()
                .ifPresent(service -> {
                    throw new IllegalArgumentException(String.format("There is already a service defined with route %s", route));
                });

        // prepate the route with the distances...
        for (int x = 1; x < route.size(); x++) {
            Stop previous = route.get(x - 1);
            Stop current = route.get(x);
            Integer distance = networkService.distanceBetweenAdjacent(previous.getStation(), current.getStation());
            current.setWhen(previous.getWhen().plusMinutes(distance));
        }

        int duration = route.get(route.size() - 1).getWhen().get(ChronoField.MINUTE_OF_DAY);

        // create service instances according to the given frequency...
        int numberOfServicesIn24Hours = ((24 * 60) - duration) / frequency;
        List<Service> newServices = IntStream.iterate(frequency, m -> m + frequency)
                .limit(numberOfServicesIn24Hours)
                .boxed()
                .map(startTime -> newService(route, startTime))
                .collect(Collectors.toList());

        services.addAll(newServices);
        return newServices;
    }


    private Service newService(List<Stop> route, Integer startTime) {

        List<Stop> cloned = route.stream()
                .map(stop -> stop.toBuilder().when(stop.getWhen().plusMinutes(startTime)).build())
                .collect(Collectors.toList());

        Service service = Service.builder()
                .id(nextId())
                .route(new LinkedList<>(cloned))
                .build();

        service.setName(service.getCurrentName());

        service.getRoute()
                .forEach(stop -> stop.setService(service));
        return service;
    }

    @Override
    public List<Service> createNewServices(Queue<String> inputs) {
        Integer frequency = Integer.parseInt(inputs.poll());
        Station start = networkService.getStationOrThrow(inputs.poll());
        Station finish = networkService.getStationOrThrow(inputs.poll());
        List<Stop> route = networkService.calculateRoute(start, finish);

        return createNewServices(frequency, route);
    }


}
