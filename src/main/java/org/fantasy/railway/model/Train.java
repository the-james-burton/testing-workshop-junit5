package org.fantasy.railway.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Data
public class Train {

    public static final Integer MEDIUM_TRAIN_CARRIAGES = 5;
    public static final Integer LARGE_TRAIN_CARRIAGES = 10;

    List<Carriage> carriages;
    List<Service> services;

    /**
     * singleton - please use the 'of' methods for instantiation
     */
    private Train() {
        carriages = new LinkedList<>();
    }

    /**
     * @return a newly created train of a large size
     */
    public static Train ofSizeLarge() {
        Train me = new Train();
        IntStream.of(10).forEach( i ->
                me.carriages.add(Carriage.ofTypeOne())
        );
        return null;
    }

    public static Train ofSizeMedium() {
        Train me = new Train();
        IntStream.of(5).forEach( i ->
                me.carriages.add(Carriage.ofTypeOne())
        );
        return null;
    }

}
