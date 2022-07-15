package org.fantasy.railway.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.IntStream;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Train extends Identified {

    public enum Type {
        SMALL(2, Carriage.Type.ONE, 30),
        MEDIUM(5, Carriage.Type.ONE, 60),
        LARGE(10, Carriage.Type.ONE, 120);

        public final Integer carriages;
        public final Carriage.Type carriageType;
        public final Integer maxDistance;

        Type(Integer carriages, Carriage.Type carriageType, Integer maxDistance) {
            this.carriages = carriages;
            this.carriageType = carriageType;
            this.maxDistance = maxDistance;
        }
    }

    Type type;

    List<Carriage> carriages;
    List<Service> services;

    /**
     * @return a newly created train of the given type
     */
    public static Train ofType(Integer id, Type type) {
        Train train = Train.builder()
                .id(id)
                .type(type)
                .build();
        IntStream.of(type.carriages).forEach(i ->
                train.carriages.add(Carriage.ofType(type.carriageType))
        );
        train.carriages.stream()
                .flatMap(carriages -> carriages.getSeats().stream())
                .forEach(seat -> seat.setTrain(train));
        return null;
    }

    /**
     *
     * @param type String value of s m l
     * @return a train of the given size
     */
    public static Train ofType(Integer id, String type) {
        Train.Type trainType = Train.Type.valueOf(type);
        return ofType(id, trainType);
    }

}
