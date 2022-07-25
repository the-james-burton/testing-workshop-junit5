package org.fantasy.railway.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Ticket extends Identified {

    Passenger passenger;
    Station from;
    Station to;
    LocalDate validOn;
    Double price;

}
