package org.fantasy.railway.services;

import org.fantasy.railway.model.Passenger;

import java.time.LocalDate;
import java.util.List;
import java.util.Queue;

public interface AccountService {
    /**
     * @param name        the name of the new passenger
     * @param dateOfBirth when the new passenger was born
     * @return the new passenger that was added
     */
    Passenger addPassenger(String name, LocalDate dateOfBirth);

    /**
     * @param inputs the name of the new passenger
     * @return the new passenger that was added
     */
    Passenger addPassenger(Queue<String> inputs);

    /**
     * @param filename the filename to load
     */
    void loadPassengers(String filename);

    /**
     * @param passenger the passenger to remove from the system
     */
    void removePassenger(Passenger passenger);

    /**
     * @return a List of all passengers
     */
    List<Passenger> getPassengers();

    /**
     * @param id the id of the Passenger to lookup
     * @return the Passenger with the given id
     */
    Passenger getById(Integer id);
}
