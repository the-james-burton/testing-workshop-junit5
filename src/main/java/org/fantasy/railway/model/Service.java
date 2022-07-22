package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Service extends Identified implements Comparable<Service> {

    Train train;
    Journey journey;

    @Builder.Default
    List<Ticket> reservations = new ArrayList<>();

    public LocalDateTime getFinishTime() {
        return getStartTime().plusMinutes(journey.totalTime());
    }

    @Override
    public int compareTo(Service that) {
        return getFinishTime().compareTo(that.getFinishTime());
    }

    public LocalDateTime getStartTime() {
        return journey.getRoute().get(0).getWhen();
    }
}
