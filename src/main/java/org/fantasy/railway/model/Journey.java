package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Journey {
    List<Stop> route;
    Ticket ticket;

    /**
     *
     * @return the total time take for this journey
     */
    public Integer totalTime() {
        if (route == null) {
            throw new IllegalStateException("route is null but should be a list");
        }
        return route.stream()
                .map(stop -> stop.getMinutes())
                .reduce(0, (a, b) -> a + b);
    }

}
