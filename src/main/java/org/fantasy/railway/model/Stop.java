package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class Stop {
    Station station;
    LocalTime when;
}
