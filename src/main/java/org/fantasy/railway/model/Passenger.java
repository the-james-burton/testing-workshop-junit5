package org.fantasy.railway.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Passenger {

    String id;
    String name;
    LocalDate dateOfBirth;
    List<Concession> concessions;

}
