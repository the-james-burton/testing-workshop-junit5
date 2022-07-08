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

    public enum Type {
        ONE, TWO, THREE;
    }

    /**
     *
     * @param type the type of carraige to create
     * @return a newly created carriage of the given type
     */
    public static Carriage ofType(Carriage.Type type) {
        switch (type) {
            case ONE:
                return ofTypeOne();
        }
        throw new IllegalArgumentException(String.format("Carriage of type %s not supported", type));
    }

    private static Carriage ofTypeOne() {
        Carriage carriage = new Carriage();
        // six rows facing forwards
        IntStream.of(6).forEach(i ->
            carriage.seats.addAll(twoAndTwoRow(false, true))
        );
        // table row
        carriage.getSeats().addAll(twoAndTwoRow(true, true));
        carriage.getSeats().addAll(twoAndTwoRow(true, false));
        // backwards and forwards row
        carriage.getSeats().addAll(twoAndTwoRow(false, false));
        carriage.getSeats().addAll(twoAndTwoRow(false, true));
        // table row
        carriage.getSeats().addAll(twoAndTwoRow(true, true));
        carriage.getSeats().addAll(twoAndTwoRow(true, false));
        // six rows facing backwards
        IntStream.of(6).forEach(i ->
                carriage.seats.addAll(twoAndTwoRow(false, false))
        );
        carriage.seats.stream()
                .forEach(seat -> seat.setCarriage(carriage));
        return carriage;
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
