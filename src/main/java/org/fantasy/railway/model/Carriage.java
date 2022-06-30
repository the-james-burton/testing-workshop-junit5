package org.fantasy.railway.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Carriage {

    @Getter
    List<Seat> seats;

    /**
     * singleton - please use the 'of' methods for instantiation
     */
    private Carriage() {
        seats = new LinkedList<>();
    }

    /**
     * @return a new ly created carriage of type 1
     */
    public static Carriage ofTypeOne() {
        Carriage me = new Carriage();
        // six rows facing forwards
        IntStream.of(6).forEach(i ->
            me.seats.addAll(twoAndTwoRow(false, true))
        );
        // table row
        me.getSeats().addAll(twoAndTwoRow(true, true));
        me.getSeats().addAll(twoAndTwoRow(true, false));
        // backwards and forwards row
        me.getSeats().addAll(twoAndTwoRow(false, false));
        me.getSeats().addAll(twoAndTwoRow(false, true));
        // table row
        me.getSeats().addAll(twoAndTwoRow(true, true));
        me.getSeats().addAll(twoAndTwoRow(true, false));
        // six rows facing backwards
        IntStream.of(6).forEach(i ->
                me.seats.addAll(twoAndTwoRow(false, false))
        );
        return me;
    }

    private static List<Seat> twoAndTwoRow(Boolean hasTable, Boolean isFacingForwards) {
        List<Seat> row = new LinkedList<>();
        row.add(Seat.builder().window(true).aisle(false).table(hasTable).facingForwards(isFacingForwards).build());
        row.add(Seat.builder().window(false).aisle(true).table(hasTable).facingForwards(isFacingForwards).build());
        row.add(Seat.builder().window(false).aisle(true).table(hasTable).facingForwards(isFacingForwards).build());
        row.add(Seat.builder().window(true).aisle(false).table(hasTable).facingForwards(isFacingForwards).build());
        return row;
    }


}
