package org.fantasy.railway.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fantasy.railway.util.TestUtils.newAdult;
import static org.fantasy.railway.util.TestUtils.newPensioner;
import static org.fantasy.railway.util.TestUtils.newYoungPerson;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PassengerTest {

    @Test
    void shouldAddConcessionIfNotPresentAndPassengerQualifies() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldNotAddConcessionIfPassengerDoesNotQualify() {
        Passenger passenger = newAdult();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                passenger.addConcession(Concession.YOUNG_PERSONS_RAILCARD)
        );

        String expected = "Passenger 3 does not qualify for concession YOUNG_PERSONS_RAILCARD";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldNotAddConcessionIfPresent() {
        Passenger passenger = newPensioner();
        passenger.addConcession(Concession.PENSIONER_DISCOUNT);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                passenger.addConcession(Concession.PENSIONER_DISCOUNT)
        );

        String expected = "Passenger 4 already has concession PENSIONER_DISCOUNT";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldRemoveConcessionIfPresent() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldNotRemoveConcessionIfNotPresent() {
        Passenger passenger = newYoungPerson();
        passenger.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                passenger.removeConcession(Concession.PENSIONER_DISCOUNT)
        );
        String expected = "Passenger 2 does not have concession PENSIONER_DISCOUNT";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGiveNoDiscountWithNoConcessions() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldGiveDiscountWithOneConcession() {
        // TODO EXERCISE 2
    }

    @Test
    void shouldGiveDiscountWithTwoConcessions() {
        // TODO EXERCISE 2
    }
}