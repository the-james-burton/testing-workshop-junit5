package org.fantasy.railway.services;

import com.google.common.collect.Comparators;
import lombok.Getter;
import org.fantasy.railway.model.Concession;
import org.fantasy.railway.model.Passenger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class AccountService {

    @Getter
    List<Passenger> passengers;

    public AccountService() {
        passengers = new ArrayList<>();
    }


    private Integer nextPassengerId() {
        if (passengers.size() == 0) {
            return 1;
        }
        return passengers.stream()
                .max(Comparator.comparing(Passenger::getId))
                .map(Passenger::getId)
                .get() + 1;
    }

    /**
     *
     * @param id the id of the Passenger to get
     * @return the Passenger with the given id
     */
    public Passenger getPassengerById(Integer id) {
        return passengers.stream()
                .filter(passenger -> id.equals(passenger.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No passenger with id $s", id)));
    }
    /**
     *
     * @param name the name of the new passenger
     * @param dateOfBirth when the new passenger was born
     */
    public void addPassenger(String name, LocalDate dateOfBirth) {
        Passenger passenger = Passenger.builder()
                .id(nextPassengerId())
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
        if (passenger.getConcessions().contains(concession)) {
            throw new IllegalStateException(String.format("Passenger %s does not have concession %s", passenger, concession));
        }
        passenger.getConcessions().remove(concession);
    }
}
