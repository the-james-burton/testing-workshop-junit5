package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Passenger extends Identified {

    String name;
    LocalDate dateOfBirth;

    @Builder.Default
    List<Concession> concessions = new ArrayList<>();

    @Builder.Default
    List<Ticket> tickets = new ArrayList<>();

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
