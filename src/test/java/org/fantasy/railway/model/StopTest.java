package org.fantasy.railway.model;

import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class StopTest {

    Stop emptyStop;
    Stop firstStop;
    Stop secondStop;

    @BeforeEach
    void setup() {
        emptyStop = Stop.builder().build();

        firstStop = Stop.builder()
                .station(Station.builder().name("First stop").build())
                .when(LocalTime.of(10, 0))
                .build();

        secondStop = Stop.builder()
                .station(Station.builder().name("Second stop").build())
                .when(LocalTime.of(10, 5))
                .build();
    }

    @Test
    void shouldHaveDefaultTimeOnCreation() {
        assertThat(emptyStop.getWhen()).isNotNull();
        assertThat(emptyStop.getWhen()).isEqualTo(LocalTime.of(0, 0));
    }

    @Test
    void shouldSortByWhen() {
        assertThat(secondStop).isGreaterThan(firstStop);
    }

}
