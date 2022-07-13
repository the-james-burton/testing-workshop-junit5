package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Service extends Identified implements Comparable<Service> {

    LocalDateTime startTime;
    Train train;
    Journey journey;
    List<Ticket> reservations;

    public LocalDateTime getFinishTime() {
        return startTime.plusMinutes(journey.totalTime());
    }

    @Override
    public int compareTo(Service that) {
        return getFinishTime().compareTo(that.getFinishTime());
    }
}
