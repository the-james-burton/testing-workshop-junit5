package org.fantasy.railway.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class ConcessionTest {

    @ParameterizedTest
    @EnumSource(Concession.class) // passing all concessions
    void shouldHaveMinimumAgeAboveTwelve(Concession concession) {
        assertThat(concession.getMinimumAge()).isGreaterThanOrEqualTo(13);
    }

    @ParameterizedTest
    @EnumSource(Concession.class)
    void shouldHaveMaxAgeBelowOneHundred(Concession concession) {
        assertThat(concession.getMaximumAge()).isLessThan(100);
    }

    @ParameterizedTest
    @EnumSource(Concession.class)
    void shouldHaveDiscountLessThanOne(Concession concession) {
        assertThat(concession.getDiscount()).isLessThan(1.0d);
    }

}
