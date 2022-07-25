package org.fantasy.railway.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.fantasy.railway.util.RailwayUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Service extends Identified implements Comparable<Service> {

    List<Stop> route;

    public LocalTime getStartTime() {
        return route.get(0).getWhen();
    }

    public LocalTime getFinishTime() {
        return route.get(route.size()-1).getWhen();
    }

    @Override
    public int compareTo(Service that) {
        return getFinishTime().compareTo(that.getFinishTime());
    }

}
