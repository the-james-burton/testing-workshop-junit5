package org.fantasy.railway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public enum Concession {

    YOUNG_PERSONS_RAILCARD(
            13, 25,
            LocalTime.parse("09:30:00"),
            LocalTime.parse("16:30"),
            0.2d),
    PENSIONER_DISCOUNT(
            65, 125,
            LocalTime.parse("09:30:00"),
            LocalTime.parse("23:59:59"),
            0.2d);

    private final Integer minimumAge;
    private final Integer maximumAge;
    private final LocalTime earliestTime;
    private final LocalTime latestTime;
    private final Double discount;

}
