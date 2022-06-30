package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Seat {

    Boolean table;
    Boolean window;
    Boolean aisle;
    Boolean facingForwards;
    List<Ticket> reservations;
}
