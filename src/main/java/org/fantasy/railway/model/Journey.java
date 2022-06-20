package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Journey {
    List<Stop> route;
    Ticket ticket;
}
