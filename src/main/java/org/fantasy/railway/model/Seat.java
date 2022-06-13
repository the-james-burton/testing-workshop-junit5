package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Seat {

    Boolean hasTable;
    Boolean hasChargingPoint;
    Boolean isWindow;
    Boolean isAisle;
    List<Ticket> reservations;
}
