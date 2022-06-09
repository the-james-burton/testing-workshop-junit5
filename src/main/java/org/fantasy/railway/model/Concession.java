package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class Concession {

    Integer minimumAge;
    Integer maximumAge;
    LocalTime earliestTime;
    LocalTime latestTime;
    Double discount;

}
