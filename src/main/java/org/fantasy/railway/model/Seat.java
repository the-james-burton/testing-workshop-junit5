package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class Seat {

    List<Ticket> reservations;

    /**
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
