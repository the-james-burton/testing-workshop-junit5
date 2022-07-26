package org.fantasy.railway.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Service extends Identified implements Comparable<Service> {

    @Builder.Default
    Queue<Stop> route = new LinkedList<>();

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
                .max(Comparator.naturalOrder())
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
                        .min(Comparator.naturalOrder()).get()
                        .getStation().getName(),
                route.stream()
                        .max(Comparator.naturalOrder()).get()
                        .getStation().getName()
        );

    }

}
