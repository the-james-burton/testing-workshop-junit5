package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Seat {

    Boolean hasTable;
    Boolean hasChargingPoint;

}
