package org.fantasy.railway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Concession {

    YOUNG_PERSONS_RAILCARD(
            13, 25,
            0.2d),
    PENSIONER_DISCOUNT(
            65, 99,
            0.4d),

    DISABLED_DISCOUNT(
            13, 99,
            0.2d);

    public final Integer minimumAge;
    public final Integer maximumAge;
    public final Double discount;


}
