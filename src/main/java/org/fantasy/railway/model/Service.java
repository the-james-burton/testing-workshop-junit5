package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Map;

@Data
@Builder
public class Service {

    Map<Station, LocalTime> route;
    Train train;

}
