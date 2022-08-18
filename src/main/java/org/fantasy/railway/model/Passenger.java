package org.fantasy.railway.model;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.fantasy.railway.util.Now;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents someone travelling on the fantasy railway network.
 * They may have a list of concessions providing discounts to ticket prices.
 * They may also have one or more tickets they have purchased for travel.
 */
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Passenger extends Identified {

    private String name;
    private LocalDate dateOfBirth;

    @Builder.Default
    private List<Concession> concessions = new ArrayList<>();

    @Builder.Default
    private List<Ticket> tickets = new ArrayList<>();


    /**
     * @return the total discount amount from all concessions (will not be greater than 1.0)
     */
    public Double totalDiscount() {
        Double discount = concessions.stream()
                .map(Concession::getDiscount)
                .reduce(0.0d, Double::sum);

        return Math.min(1.0d, discount);
    }

    /**
     * adds the given concession to the passenger, if this passenger qualifies
     * @param concession the concession to add to this passenger
     */
    public void addConcession(Concession concession) {
        Preconditions.checkArgument(!concessions.contains(concession),
                "Passenger %s already has concession %s", id, concession);

        Predicate<Concession> tooYoung = c -> dateOfBirth.plusYears(c.getMinimumAge()).isAfter(Now.localDate());
        Predicate<Concession> tooOld = c -> dateOfBirth.plusYears(c.getMaximumAge()).isBefore(Now.localDate());

        Preconditions.checkArgument(!tooYoung.or(tooOld).test(concession),
                "Passenger %s does not qualify for concession %s", id, concession);

        concessions.add(concession);

    }

    /**
     * removes the given concession from this passenger
     * @param concession the concession to remove from this passenger
     */
    public void removeConcession(Concession concession) {
        Preconditions.checkArgument(concessions.contains(concession),
                "Passenger %s does not have concession %s", id, concession);

        concessions.remove(concession);
    }


}
