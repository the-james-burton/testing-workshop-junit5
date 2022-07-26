package org.fantasy.railway.model;

import org.assertj.core.api.Assertions;
import org.fantasy.railway.util.Now;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PassengerTest {

    Passenger child;
    Passenger youngPerson;
    Passenger adult;
    Passenger pensioner;

    @BeforeEach
    void setup() {
        child = Passenger.builder().id(1).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(4))
                .build();
        youngPerson = Passenger.builder().id(1).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(17))
                .build();
        adult = Passenger.builder().id(1).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(35))
                .build();
        pensioner = Passenger.builder().id(1).name("Alice")
                .dateOfBirth(Now.localDate().minusYears(72))
                .build();
    }

    @Test
    void shouldAddConcessionIfNotPresentAndPassengerQualifies() {
        youngPerson.addConcession(Concession.YOUNG_PERSONS_RAILCARD);

        assertThat(youngPerson.getConcessions()).contains(Concession.YOUNG_PERSONS_RAILCARD);
    }

    @Test
    void shouldNotAddConcessionIfNotPresentAndPassengerDoesNotQualify() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            child.addConcession(Concession.YOUNG_PERSONS_RAILCARD)
        );

        String expected = "Passenger 1 does not qualify for concession YOUNG_PERSONS_RAILCARD";
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
        String expected = "Passenger 1 does not have concession PENSIONER_DISCOUNT";
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