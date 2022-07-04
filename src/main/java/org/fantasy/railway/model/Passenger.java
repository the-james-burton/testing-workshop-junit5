package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class Passenger {

    String id;
    String name;
    LocalDate dateOfBirth;
    List<Concession> concessions;
    List<Ticket> tickets;

    /**
     *
     * @param when time of day to calculate concessions for
     * @return the total discount amount (will not be greater than 1.0)
     */
    public Double totalDiscount(LocalTime when) {
        Double discount = concessions.stream()
                .filter(concession -> concession.getEarliestTime().isBefore(when))
                .filter(concession -> concession.getLatestTime().isAfter(when))
                .map(Concession::getDiscount)
                .reduce(0.0d, Double::sum);

        return Math.min(1.0d, discount);
    }
}
