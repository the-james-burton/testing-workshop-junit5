package org.fantasy.railway.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Data
public class Train {

    public enum Type {
        SMALL(2, Carriage.Type.ONE, 30),
        MEDIUM(5, Carriage.Type.ONE, 60),
        LARGE(10, Carriage.Type.ONE, 120);

        public final Integer carriges;
        public final Carriage.Type carraigeType;
        public final Integer maxDistance;

        Type(Integer carriages, Carriage.Type carriageType, Integer maxDistance) {
            this.carriges = carriages;
            this.carraigeType = carriageType;
            this.maxDistance = maxDistance;
        }
    }

    Type type;

    List<Carriage> carriages;
    List<Service> services;

    /**
     * singleton - please use the 'of' methods for instantiation
     */
    private Train(Type type) {
        this.type = type;
        this.carriages = new LinkedList<>();
    }

    /**
     * @return a newly created train of the given type
     */
    public static Train ofType(Type type) {
        Train train = new Train(type);
        IntStream.of(type.carriges).forEach( i ->
                train.carriages.add(Carriage.ofType(type.carraigeType))
        );
        train.carriages.stream()
                .flatMap(carriages -> carriages.getSeats().stream())
                .forEach(seat -> seat.setTrain(train));
        return null;
    }

}
