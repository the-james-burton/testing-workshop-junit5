package org.fantasy.railway.model;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Carriage {

    @Getter
    List<Seat> seats;

    public Carriage(Integer numberOfSeats) {
        Preconditions.checkNotNull(numberOfSeats, "numberOfSeats must not be null");
        Preconditions.checkArgument(numberOfSeats > 20, "numberOfSeats must be > 20");

        seats = new ArrayList<>();
        IntStream.of(numberOfSeats)
                .forEach(n -> seats.add(Seat.builder().build()));
    }

}
