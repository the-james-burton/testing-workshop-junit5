package org.fantasy.railway.services;

import lombok.Getter;
import org.fantasy.railway.model.Concession;
import org.fantasy.railway.model.Passenger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountService extends BaseService<Passenger> {

    @Getter
    List<Passenger> passengers;

    public AccountService() {
        passengers = new ArrayList<>();
    }

    @Override
    List<Passenger> getItems() {
        return passengers;
    }

    /**
     *
     * @param name the name of the new passenger
     * @param dateOfBirth when the new passenger was born
     */
    public void addPassenger(String name, LocalDate dateOfBirth) {
        Passenger passenger = Passenger.builder()
                .id(nextId())
                .name(name)
                .dateOfBirth(dateOfBirth)
                .build();
        passengers.add(passenger);
    }

    /**
     *
     * @param passenger the passenger to remove from the system
     */
    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        // TODO - throw if passenger has reservation or current ticket
    }

    /**
     *
     * @param passenger the passenger to add the concession to
     * @param concession the concession to add to the given passenger
     */
    public void addConcenssion(Passenger passenger, Concession concession) {
        if (passenger.getConcessions().contains(concession)) {
            throw new IllegalStateException(String.format("Passenger %s already has concession %s", passenger, concession));
        }
        passenger.getConcessions().add(concession);
    }

    /**
     *
     * @param passenger the passenger to add the concession to
     * @param concession the concession to add to the given passenger
     */
    public void removeConcenssion(Passenger passenger, Concession concession) {
        if (!passenger.getConcessions().contains(concession)) {
            throw new IllegalStateException(String.format("Passenger %s does not have concession %s", passenger, concession));
        }
        passenger.getConcessions().remove(concession);
    }

}
