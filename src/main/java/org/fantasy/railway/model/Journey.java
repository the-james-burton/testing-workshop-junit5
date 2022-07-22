package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Data
@Builder
public class Journey {

    @Builder.Default
    List<Stop> route = new LinkedList<>();

    /**
     * @return the total time take for this journey
     */
    public Integer totalTime() {
        if (route == null) {
            throw new IllegalStateException("route is null but should be a list");
        }
        return route.stream()
                .map(Stop::getMinutes)
                .reduce(0, Integer::sum);
    }

    public List<Stop> getCumulativeTimeRoute() {
        AtomicInteger cumulative = new AtomicInteger();
        return route.stream()
                .map(stop -> Stop.builder()
                        .station(stop.getStation())
                        .minutes(cumulative.addAndGet(stop.getMinutes()))
                        .build())
                .collect(Collectors.toList());
    }

}
