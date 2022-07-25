package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
public class Stop implements Comparable<Stop> {

    @ToString.Exclude
    Service service;
    Station station;

    @Builder.Default
    LocalTime when = LocalTime.of(0,0);

    @Override
    public int compareTo(Stop that) {
        return when.compareTo(that.when);
    }
}
