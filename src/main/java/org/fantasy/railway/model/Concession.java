package org.fantasy.railway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public enum Concession {

    YOUNG_PERSONS_RAILCARD(
            13, 25,
            0.2d),
    PENSIONER_DISCOUNT(
            65, 125,
            0.4d);

    public final Integer minimumAge;
    public final Integer maximumAge;
    public final Double discount;


}
