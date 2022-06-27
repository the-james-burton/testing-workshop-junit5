package org.fantasy.railway.model;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

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
     * @return a new carriage of type 1
     */
    public static Carriage ofTypeOne() {
        Carriage me = new Carriage();

        return me;
    }

    private List<Seat> twoByTwoRow(Boolean table) {
        List<Seat> row = new LinkedList<>();
        // TODO implementation
        return row;
    }


}
