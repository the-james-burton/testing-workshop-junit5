package org.fantasy.railway.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.fantasy.railway.util.RailwayUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Ticket extends Identified {

    @ToString.Exclude
    Passenger passenger;
    Station from;
    Station to;
    LocalDate validOn;
    BigDecimal price;

    /**
     * @param price a double for the raw price value
     */
    public void setPrice(Double price) {
        this.price = RailwayUtils.parsePrice(price);
    }

}
