package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

/**
 * This class represents a stop made by train service at a certain time when on its route
 */
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

    /**
     *
     * @param stop the Stop to compare with
     * @return true if this stop is at the same station as the given stop
     */
    public boolean sameStationAs(Stop stop) {
        return station.equals(stop.station);
    }
}
