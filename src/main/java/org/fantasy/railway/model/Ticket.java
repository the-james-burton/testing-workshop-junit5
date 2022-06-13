package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
public class Ticket {

    Passenger passenger;
    Service service;
    Station from;
    Station to;
    Seat reservation;
    Double price;

}
