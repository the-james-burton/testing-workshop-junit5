package org.fantasy.railway.util;

import org.fantasy.railway.model.Passenger;
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

        route.add(firstStop());
        route.add(secondStop());
        route.add(thirdStop());

        return route;
    }

    public static Passenger newYoungPerson() {
        return Passenger.builder().id(2).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(17))
                .build();
    }

    public static Passenger newAdult() {
        return Passenger.builder().id(3).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(35))
                .build();

    }

    public static Passenger newPensioner() {
        return Passenger.builder().id(4).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(72))
                .build();
    }

    public static Stop firstStop() {
        return Stop.builder()
                .station(Station.builder().name("First").build())
                .when(LocalTime.of(10, 0))
                .build();
    }

    public static Stop secondStop() {
        return Stop.builder()
                .station(Station.builder().name("Second").build())
                .when(LocalTime.of(10, 4))
                .build();
    }

    public static Stop thirdStop() {
        return Stop.builder()
                .station(Station.builder().name("Third").build())
                .when(LocalTime.of(10, 9))
                .build();
    }

    public static Stop emptyStop() {
        return Stop.builder().build();
    }
}
