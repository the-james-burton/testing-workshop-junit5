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
        Passenger passenger = newYoungPerson();
        passenger.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        assertThat(passenger.getConcessions()).contains(Concession.YOUNG_PERSONS_RAILCARD);
    }

    @Test
    void shouldRemoveConcessionIfPresent() {
        Passenger passenger = newYoungPerson();
        passenger.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        passenger.removeConcession(Concession.YOUNG_PERSONS_RAILCARD);

        assertThat(passenger.getConcessions()).doesNotContain(Concession.YOUNG_PERSONS_RAILCARD);
    }

    @Test
    void shouldGiveNoDiscountWithNoConcessions() {
        Double discount = newYoungPerson().totalDiscount();

        assertThat(discount).isEqualTo(0.0d);
    }

    @Test
    void shouldGiveDiscountWithOneConcession() {
        Passenger passenger = newYoungPerson();
        passenger.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        Double discount = passenger.totalDiscount();

        assertThat(discount).isEqualTo(0.2d);
    }

    @Test
    void shouldGiveDiscountWithTwoConcessions() {
        Passenger passenger = newYoungPerson();
        passenger.addConcession(Concession.YOUNG_PERSONS_RAILCARD);
        passenger.addConcession(Concession.DISABLED_DISCOUNT);

        Double discount = passenger.totalDiscount();

        assertThat(discount).isEqualTo(0.4d);
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

}