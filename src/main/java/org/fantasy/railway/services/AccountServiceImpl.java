package org.fantasy.railway.services;

import lombok.Getter;
import org.fantasy.railway.model.Concession;
import org.fantasy.railway.model.Passenger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl extends BaseService<Passenger> implements AccountService {

    @Getter
    List<Passenger> passengers;

    public AccountServiceImpl() {
        passengers = new ArrayList<>();
    }

    @Override
    List<Passenger> getItems() {
        return passengers;
    }

    @Override
    public void addPassenger(String name, LocalDate dateOfBirth) {
        Passenger passenger = Passenger.builder()
                .id(nextId())
                .name(name)
                .dateOfBirth(dateOfBirth)
                .build();
        passengers.add(passenger);
    }

    @Override
    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        // TODO - throw if passenger has reservation or current ticket
    }

    @Override
    public void addConcenssion(Passenger passenger, Concession concession) {
        if (passenger.getConcessions().contains(concession)) {
            throw new IllegalStateException(String.format("Passenger %s already has concession %s", passenger, concession));
        }
        passenger.getConcessions().add(concession);
    }

    @Override
    public void removeConcenssion(Passenger passenger, Concession concession) {
        if (!passenger.getConcessions().contains(concession)) {
            throw new IllegalStateException(String.format("Passenger %s does not have concession %s", passenger, concession));
        }
        passenger.getConcessions().remove(concession);
    }

}
