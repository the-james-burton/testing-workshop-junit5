package org.fantasy.railway.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceTest {

    Service emptyService;

    @BeforeEach
    void setup() {
        emptyService = Service.builder().build();
    }


    @Test
    void shouldHaveEmptyRouteOnCreation() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldReturnStartTime() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldReturnFinishTime() {
        // TODO EXERCISE 2
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
        // TODO EXERCISE 2
    }

    @Test
    void shouldReturnExpectedMessageForCurrentName() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldSortByFinishTime() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldHaveSameRouteAs() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldNotHaveSameRouteAsIfDifferentSize() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldNotHaveSameRouteAsIfDifferentStops() {
        // TODO EXERCISE 2
    }

}