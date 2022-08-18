package org.fantasy.railway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A discount which is available to qualifying passengers. One or more can be added to the passenger.
 */
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

    /**
     * minimum age of the passenger that the concession can be applied to
     */
    public final Integer minimumAge;

    /**
     * maximum age of the passenger that the concession can be applied to
     */
    public final Integer maximumAge;

    /**
     * the amount of discount to the prive that this concession provides, in a range of 0-1. For example, a 0.2 discount here represents 20% off the regular price.
     */
    public final Double discount;


}
