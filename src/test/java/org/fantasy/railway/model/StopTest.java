package org.fantasy.railway.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.firstStop;

class StopTest {

    @Test
    void shouldHaveDefaultTimeOnCreation() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldSortByWhen() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldHaveSameStationAs() {
        // RUN IN EXERCISE 1

        // arrange
        Stop stop1 = firstStop();
        Stop stop2 = firstStop();
        stop2.setWhen(stop2.getWhen().plusMinutes(15));

        // act & assert
        assertThat(stop1.sameStationAs(stop2)).isTrue();
    }

    @Test
    void shouldNotHaveSameStationAs() {
        // TODO EXERCISE 2
    }

}
