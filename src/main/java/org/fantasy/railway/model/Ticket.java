package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Ticket extends Identified {

    Passenger passenger;
    Service service;
    Journey journey;
    Seat reservation;
    Double price;
    Boolean purchased;

}
