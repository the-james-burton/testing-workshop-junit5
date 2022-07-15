package org.fantasy.railway.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
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
