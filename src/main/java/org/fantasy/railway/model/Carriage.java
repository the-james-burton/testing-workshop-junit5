package org.fantasy.railway.model;

import lombok.Getter;

import java.util.List;

public class Carriage {

    @Getter
    List<Seat> seats;

    private Carriage() {
    }

    public static Carriage ofTypeOne() {
        Carriage me = new Carriage();
        // TODO - create and add seats for standard format car
        return me;
    }

}
