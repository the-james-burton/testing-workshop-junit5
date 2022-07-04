package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Service implements Comparable<Service> {

    LocalDateTime startTime;
    Train train;
    Line line;
    Journey journey;
    List<Ticket> reservations;

    public LocalDateTime finishTime() {
        return startTime.plusMinutes(journey.totalTime());
    }

    @Override
    public int compareTo(Service that) {
        return finishTime().compareTo(that.finishTime());
    }
}
