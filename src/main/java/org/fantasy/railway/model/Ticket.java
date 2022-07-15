package org.fantasy.railway.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Ticket extends Identified {

    Passenger passenger;
    Service service;
    Journey journey;
    Seat reservation;
    Double price;
    Boolean purchased;

}
