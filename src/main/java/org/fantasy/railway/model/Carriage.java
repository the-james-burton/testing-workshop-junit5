package org.fantasy.railway.model;

import lombok.Getter;

import java.util.List;

public class Carriage {

    @Getter
    List<Seat> seats;

    /**
     * singleton - please use the 'of' methods for instantiation
     */
    private Carriage() {
    }

    /**
     * @return a new carriage of type 1
     */
    public static Carriage ofTypeOne() {
        Carriage me = new Carriage();
        // TODO - create and add seats for standard format car
        return me;
    }

}
