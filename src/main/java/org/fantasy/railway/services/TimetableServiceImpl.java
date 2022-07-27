package org.fantasy.railway.services;

import lombok.Getter;
import lombok.Setter;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.util.Now;
import org.fantasy.railway.util.RailwayUtils;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
    NetworkService network;

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
            if (route.isEmpty())
                continue;
            do {
                Stop current = route.peek();
                if (current.getWhen().isAfter(Now.localTime())) break;
                dispatched.add(route.poll());
            } while (!route.isEmpty());
        }

        services.removeIf(service -> service.getRoute().isEmpty());

        // TODO remove this
        System.out.format("Total %s stops visited%n%n", dispatched.size()); // TODO remove this
    }


    @Override
    public Queue<Stop> getDispatched() {
        return dispatched;
    }

    @Override
    public List<Service> createNewServices(Integer frequency, Station start, Station finish) {
        List<Stop> route = network.calculateRoute(start, finish);
        Stop first = route.get(0);
        first.setWhen(first.getWhen().plusMinutes(frequency));

        // does a service already exist with the same route?
        services.stream()
                .filter(service -> service.sameRouteAs(route))
                .findAny()
                .ifPresent(service -> {
                    throw new IllegalStateException(String.format("There is already a service defined with route %s", route));
                });

        // prepare the route with the distances...
        for (int x = 1; x < route.size(); x++) {
            Stop previous = route.get(x - 1);
            Stop current = route.get(x);
            Integer distance = network.distanceBetweenAdjacent(previous.getStation(), current.getStation());
            current.setWhen(previous.getWhen().plusMinutes(distance));
        }

        // how long does the route take to omplete?
        LocalTime startTime = route.get(0).getWhen();
        LocalTime finishTime = route.get(route.size() - 1).getWhen();
        Integer duration = (int) startTime.until(finishTime, ChronoUnit.MINUTES);

        // how many services can we fit into 24 hours?
        int numberOfServicesIn24Hours = ((24 * 60) - duration) / frequency;

        // create service instances according to the given frequency...
        List<Service> newServices = IntStream.iterate(frequency, m -> m + frequency)
                .limit(numberOfServicesIn24Hours)
                .boxed()
                .map(time -> newService(route, time))
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
        Station start = network.getStationOrThrow(inputs.poll());
        Station finish = network.getStationOrThrow(inputs.poll());

        return createNewServices(frequency, start, finish);
    }


}
