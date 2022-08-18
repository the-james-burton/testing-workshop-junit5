package org.fantasy.railway.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.createTestRoute;
import static org.fantasy.railway.util.TestUtils.firstStop;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceTest {

    Service emptyService;
    Service serviceWithRoute;

    Stop first;
    Stop second;
    Stop third;

    @BeforeEach
    void setup() {
        emptyService = Service.builder().build();

        serviceWithRoute = Service.builder().build();

        Queue<Stop> route = createTestRoute(serviceWithRoute);
        serviceWithRoute.setRoute(route);

        Iterator<Stop> stops = route.stream().iterator();
        first = stops.next();
        second = stops.next();
        third = stops.next();
    }


    @Test
    void shouldHaveEmptyRouteOnCreation() {
        assertThat(emptyService.getRoute())
                .isNotNull()
                .isEmpty();
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
    void shouldReturnCompletedMessageForCurrentNameIfEmptyRoute() {
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

    @Test
    void shouldHaveSameRouteAs() {
        List<Stop> sameRoute = createTestRoute();
        sameRoute.forEach(stop -> stop.setWhen(stop.getWhen().plusMinutes(15)));

        assertThat(serviceWithRoute.sameRouteAs(sameRoute)).isTrue();
    }

    @Test
    void shouldNotHaveSameRouteAsIfDifferentSize() {
        List<Stop> differentRoute = createTestRoute().stream()
                .skip(1)
                .collect(Collectors.toList());
        differentRoute.forEach(stop -> stop.setWhen(stop.getWhen().plusMinutes(15)));

        assertThat(serviceWithRoute.sameRouteAs(differentRoute)).isFalse();
    }

    @Test
    void shouldNotHaveSameRouteAsIfDifferentStops() {
        List<Stop> differentRoute = createTestRoute().stream()
                .skip(1)
                .collect(Collectors.toList());
        differentRoute.add(firstStop());

        assertThat(serviceWithRoute.sameRouteAs(differentRoute)).isFalse();
    }

}