package org.fantasy.railway.model;

import org.fantasy.railway.util.Now;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceTest {

    Service emptyService;
    Service serviceWithRoute;
    Queue<Stop> route;

    Stop first;
    Stop second;
    Stop third;

    @BeforeEach
    void setup() {
        emptyService = Service.builder().build();

        serviceWithRoute = Service.builder().build();

        route = new LinkedList<>();

        first = Stop.builder()
                .service(serviceWithRoute)
                .station(Station.builder().name("First stop").build())
                .when(LocalTime.of(10, 0))
                .build();

        second = Stop.builder()
                .service(serviceWithRoute)
                .station(Station.builder().name("Second stop").build())
                .when(LocalTime.of(10, 4))
                .build();

        third = Stop.builder()
                .service(serviceWithRoute)
                .station(Station.builder().name("Third stop").build())
                .when(LocalTime.of(10, 9))
                .build();

        route.add(first);
        route.add(second);
        route.add(third);

        serviceWithRoute.setRoute(route);
    }

    @Test
    void shouldReturnStartTime() {
        LocalTime expected = first.getWhen();
        LocalTime actual = serviceWithRoute.getStartTime();

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldReturnFinishTime() {
        LocalTime expected = third.getWhen();
        LocalTime actual = serviceWithRoute.getFinishTime();

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void shouldThrowExceptionInGetStartTimeIfNoRoute() {
        Exception exception = assertThrows(IllegalStateException.class, () ->
            emptyService.getStartTime()
        );

        String expectedMessage = "No route defined for this service";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void shouldThrowExceptionInGetFinishTimeIfNoRoute() {
        Exception exception = assertThrows(IllegalStateException.class, () ->
            emptyService.getFinishTime()
        );

        String expected = "No route defined for this service";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnCompletedMessageForCurrentNameIfNoRoute() {
        String actual = emptyService.getCurrentName();
        String expected = "This service has completed its journey";

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnExpectedMessageForCurrentName() {
        String actual = serviceWithRoute.getCurrentName();
        String expected = String.format("The %s from %s to %s",
                first.getWhen(),
                first.getStation().getName(),
                third.getStation().getName());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldSortByFinishTime() {
        Service laterServiceWithRoute;
        Queue<Stop> laterRoute = new LinkedList<>();
        laterServiceWithRoute = Service.builder().build();

        laterRoute.add(first.toBuilder().when(first.getWhen().plusMinutes(7)).build());
        laterRoute.add(second.toBuilder().when(second.getWhen().plusMinutes(7)).build());
        laterRoute.add(third.toBuilder().when(third.getWhen().plusMinutes(7)).build());
        laterServiceWithRoute.setRoute(laterRoute);

        assertThat(laterServiceWithRoute).isGreaterThan(serviceWithRoute);
    }

}
