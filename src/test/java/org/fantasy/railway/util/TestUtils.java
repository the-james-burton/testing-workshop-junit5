package org.fantasy.railway.util;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Stop;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class TestUtils {

    /**
     * @return a queue of stops representing a test route
     */
    public static Queue<Stop> createTestRoute(Service service) {
        Queue<Stop> route = new LinkedList<>();

        Stop first = Stop.builder()
                .service(service)
                .station(Station.builder().id(1).name("First stop").build())
                .when(LocalTime.of(10, 0))
                .build();

        Stop second = Stop.builder()
                .service(service)
                .station(Station.builder().id(2).name("Second stop").build())
                .when(LocalTime.of(10, 4))
                .build();

        Stop third = Stop.builder()
                .service(service)
                .station(Station.builder().id(3).name("Third stop").build())
                .when(LocalTime.of(10, 9))
                .build();

        route.add(first);
        route.add(second);
        route.add(third);

        return route;
    }

}
