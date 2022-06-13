package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Service {

    LocalDateTime dateTime;
    Train train;
    Line line;
    Station startStation;
    Station endStation;
    List<Ticket> tickets;

}
