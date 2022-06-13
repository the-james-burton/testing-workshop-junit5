package org.fantasy.railway.services;

import org.fantasy.railway.model.Concession;
import org.fantasy.railway.model.Passenger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AccountService {

    List<Passenger> passengers;

    /**
     *
     * @param name the name of the new passenger
     * @param dateOfBirth when the new passenger was born
     */
    void addPassenger(String name, LocalDate dateOfBirth) {
        Passenger passenger = Passenger.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .dateOfBirth(dateOfBirth)
                .build();
        passengers.add(passenger);
    }

    /**
     *
     * @param passenger the passenger to remove from the system
     */
    void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        // TODO - throw if passenger has reservation or current ticket
    }

    /**
     *
     * @param passenger the passenger to add the concession to
     * @param concession the concession to add to the given passenger
     */
    void addConcenssion(Passenger passenger, Concession concession) {
        passenger.getConcessions().add(concession);
        // TODO throw if passenger already has the given concenssion or is not eligible for it
    }
}
