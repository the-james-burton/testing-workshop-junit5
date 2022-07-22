package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Stop {
    Station station;
    Integer minutes;
    LocalDateTime when;
}
