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

    List<Carriage> carriages;
    List<Service> services;

    public static Train ofSize(Integer id, Integer numberOfCarriages) {
        Train train = Train.builder()
                .id(id)
                .build();
        IntStream.of(numberOfCarriages)
                .forEach(i -> train.carriages.add(new Carriage(40)));
        return train;
    }

}
