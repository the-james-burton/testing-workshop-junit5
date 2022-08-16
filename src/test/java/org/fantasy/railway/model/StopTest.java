package org.fantasy.railway.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.emptyStop;
import static org.fantasy.railway.util.TestUtils.firstStop;
import static org.fantasy.railway.util.TestUtils.secondStop;
import static org.fantasy.railway.util.TestUtils.thirdStop;

class StopTest {


    @Test
    void shouldHaveDefaultTimeOnCreation() {
        assertThat(emptyStop().getWhen())
                .isNotNull()
                .isEqualTo(LocalTime.of(0, 0));
    }

    @Test
    void shouldSortByWhen() {
        assertThat(thirdStop()).isGreaterThan(firstStop());
        assertThat(secondStop()).isGreaterThan(firstStop());
    }

    @Test
    void shouldHaveSameStationAs() {
        Stop stop1 = firstStop();
        Stop stop2 = firstStop();
        stop2.setWhen(stop2.getWhen().plusMinutes(15));

        assertThat(stop1.sameStationAs(stop2)).isTrue();
    }

    @Test
    void shouldNotHaveSameStationAs() {
        Stop stop1 = firstStop();
        Stop stop2 = secondStop();

        assertThat(stop1.sameStationAs(stop2)).isFalse();
    }

}
