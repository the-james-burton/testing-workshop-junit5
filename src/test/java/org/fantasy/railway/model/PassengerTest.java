package org.fantasy.railway.model;

import org.assertj.core.api.Assertions;
import org.fantasy.railway.util.Now;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PassengerTest {

    Passenger youngPerson;
    Passenger adult;
    Passenger pensioner;

    @BeforeEach
    void setup() {
        youngPerson = Passenger.builder().id(2).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(17))
                .build();
        adult = Passenger.builder().id(3).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(35))
                .build();
        pensioner = Passenger.builder().id(4).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(72))
                .build();
    }

    @Test
    void shouldAddConcessionIfNotPresentAndPassengerQualifies() {
        youngPerson.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        assertThat(youngPerson.getConcessions()).contains(Concession.YOUNG_PERSONS_RAILCARD);
    }

    @Test
    void shouldNotAddConcessionIfPassengerDoesNotQualify() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                adult.addConcession(Concession.YOUNG_PERSONS_RAILCARD)
        );

        String expected = "Passenger 3 does not qualify for concession YOUNG_PERSONS_RAILCARD";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldNotAddConcessionIfPresent() {
        pensioner.addConcession(Concession.PENSIONER_DISCOUNT);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                pensioner.addConcession(Concession.PENSIONER_DISCOUNT)
        );

        String expected = "Passenger 4 already has concession PENSIONER_DISCOUNT";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldRemoveConcessionIfPresent() {
        youngPerson.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        youngPerson.removeConcession(Concession.YOUNG_PERSONS_RAILCARD);

        assertThat(youngPerson.getConcessions()).doesNotContain(Concession.YOUNG_PERSONS_RAILCARD);
    }

    @Test
    void shouldNotRemoveConcessionIfNotPresent() {
        youngPerson.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            youngPerson.removeConcession(Concession.PENSIONER_DISCOUNT)
        );
        String expected = "Passenger 2 does not have concession PENSIONER_DISCOUNT";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGiveDiscountWithConcession() {
        youngPerson.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        Double discount = youngPerson.totalDiscount();

        assertThat(discount).isEqualTo(0.2d);
    }
}