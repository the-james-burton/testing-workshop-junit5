package org.fantasy.railway.services;

import org.fantasy.railway.model.Concession;
import org.fantasy.railway.model.Passenger;

import java.time.LocalDate;
import java.util.List;

public interface AccountService {
    /**
     * @param name        the name of the new passenger
     * @param dateOfBirth when the new passenger was born
     */
    void addPassenger(String name, LocalDate dateOfBirth);

    /**
     * @param passenger the passenger to remove from the system
     */
    void removePassenger(Passenger passenger);

    /**
     * @param passenger  the passenger to add the concession to
     * @param concession the concession to add to the given passenger
     */
    void addConcenssion(Passenger passenger, Concession concession);

    /**
     * @param passenger  the passenger to add the concession to
     * @param concession the concession to add to the given passenger
     */
    void removeConcenssion(Passenger passenger, Concession concession);

    /**
     *
     * @return a List of all passengers
     */
    List<Passenger> getPassengers();

    /**
     *
     * @param id the id of the Passenger to lookup
     * @return the Passenger with the given id
     */
    Passenger getById(Integer id);
}
