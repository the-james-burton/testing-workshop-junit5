package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class Seat {

    Train train;
    Carriage carriage;

    Boolean table;
    Boolean window;
    Boolean aisle;
    Boolean facingForwards;
    List<Ticket> reservations;

    /**
     * Useful when reserving a seat
     * If the given Seat object has null properties, they are not considered
     *
     * @param seat preferences to establish if this seat is acceptable
     * @return true if the seat matches the given seat preferences
     */
    public Boolean isSuitableFor(Seat seat) {
        Objects.requireNonNull(seat, "seat preferences cannot be null");
        return (seat.table == null || Objects.equals(table, seat.table))
                && (seat.window == null || Objects.equals(window, seat.window))
                && (seat.aisle == null || Objects.equals(aisle, seat.aisle))
                && (seat.facingForwards == null || Objects.equals(facingForwards, seat.facingForwards));
    }

    /**
     *
     * @param when to check if the seat is available
     * @return true if the seat is available (has no reservation) at the given datetime
     */
    public Boolean isAvailableAt(LocalDateTime when) {
        return reservations.stream()
                .map(Ticket::getService)
                .filter(service -> service.getStartTime().isBefore(when))
                .anyMatch(service -> service.getFinishTime().isAfter(when));
    }

    public Boolean isBookedFor(Service service) {
        return reservations.stream()
                .map(Ticket::getService)
                .anyMatch(s -> s.equals(service));
    }
}
