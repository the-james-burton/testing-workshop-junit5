package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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

    /**
     *
     * @param route the route to compare with
     * @return true if the given route is the same as this service (stops at the same stations)
     */
    public boolean sameRouteAs(List<Stop> route) {
        List<Stop> myStops = new ArrayList<>(this.route);
        List<Stop> theirStops = new ArrayList<>(route);

        if (myStops.size() != theirStops.size())
            return false;

        for (int i = 0; i < myStops.size(); i++) {
            if (!myStops.get(i).sameStationAs(theirStops.get(i)))
                return false;
        }
        return true;

    }

}
