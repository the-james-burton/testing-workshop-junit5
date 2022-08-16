package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
public class Stop implements Comparable<Stop> {

    @ToString.Exclude
    private Service service;

    private Station station;

    @Builder.Default
    private LocalTime when = LocalTime.of(0, 0);

    @Override
    public int compareTo(Stop that) {
        return when.compareTo(that.when);
    }

    public boolean sameStationAs(Stop stop) {
        return station.equals(stop.station);
    }
}
