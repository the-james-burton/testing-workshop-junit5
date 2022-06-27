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
    Boolean facingForwards;
    List<Ticket> reservations;

    public static Seat forwardFacingWindow() {
        return Seat.builder()
                .hasChargingPoint(false)
                .hasTable(false)
                .isAisle(false)
                .isWindow(true)
                .facingForwards(true)
                .build();
    }

    public Seat forwardFacingAisle() {
        return Seat.builder()
                .hasChargingPoint(false)
                .hasTable(false)
                .isAisle(true)
                .isWindow(false)
                .facingForwards(true)
                .build();
    }

}
