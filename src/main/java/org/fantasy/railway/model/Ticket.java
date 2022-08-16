package org.fantasy.railway.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.fantasy.railway.util.RailwayUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Ticket extends Identified {

    @ToString.Exclude
    private Passenger passenger;
    private Station from;
    private Station to;
    private LocalDate validOn;
    private BigDecimal price;

    /**
     * @param price a double for the raw price value
     */
    public void setPrice(Double price) {
        this.price = RailwayUtils.parsePrice(price);
    }

}
