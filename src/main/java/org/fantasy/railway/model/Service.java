package org.fantasy.railway.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Queue;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Service extends Identified implements Comparable<Service> {

    Queue<Stop> route;
    String name;

    public LocalTime getStartTime() {
        return route.stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("No route defined for this service"))
                .getWhen();
    }

    public LocalTime getFinishTime() {
        return route.stream()
                .max(Comparator.reverseOrder())
                .orElseThrow(() ->
                        new IllegalStateException("No route defined for this service"))
                .getWhen();
    }

    @Override
    public int compareTo(Service that) {
        return getFinishTime().compareTo(that.getFinishTime());
    }

    public String getCurrentName() {
        if (route.isEmpty())
            return "This service has completed its journey";

        return String.format("The %s from %s to %s",
                getStartTime(),
                route.stream()
                        .findFirst().orElseThrow(() ->
                                new IllegalArgumentException("Service has no route defined"))
                        .getStation().getName(),
                route.stream()
                        .sorted(Comparator.reverseOrder())
                        .findFirst().orElseThrow(() ->
                                new IllegalArgumentException("Service has no route defined"))
                        .getStation().getName()
        );

    }

}
