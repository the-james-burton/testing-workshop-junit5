package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class Service {

    LocalTime startTime;
    Train train;
    Line line;
    Station startStation;
    Station endStation;

}
