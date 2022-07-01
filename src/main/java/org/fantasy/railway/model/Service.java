package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Service implements Comparable<Service> {

    LocalDateTime when;
    Train train;
    Line line;
    Journey journey;
    List<Ticket> tickets;

    public LocalDateTime finishTime() {
        return when.plusMinutes(journey.totalTime());
    }

    @Override
    public int compareTo(Service that) {
        return finishTime().compareTo(that.finishTime());
    }
}
