package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Passenger extends Identified {

    String name;
    LocalDate dateOfBirth;
    List<Concession> concessions;
    List<Ticket> tickets;

    @Builder
    private Passenger(Integer id, String name, LocalDate dateOfBirth) {
        super(id);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     *
     * @param when time of day to calculate concessions for
     * @return the total discount amount (will not be greater than 1.0)
     */
    public Double totalDiscount(LocalDateTime when) {
        Double discount = concessions.stream()
                .filter(concession -> concession.getEarliestTime().isBefore(when.toLocalTime()))
                .filter(concession -> concession.getLatestTime().isAfter(when.toLocalTime()))
                .map(Concession::getDiscount)
                .reduce(0.0d, Double::sum);

        return Math.min(1.0d, discount);
    }
}
