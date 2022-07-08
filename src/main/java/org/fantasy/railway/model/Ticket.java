package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class Ticket {

    Integer id;
    Passenger passenger;
    Service service;
    Journey journey;
    Seat reservation;
    Double price;
    Boolean purchased;

}
