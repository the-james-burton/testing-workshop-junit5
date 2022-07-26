package org.fantasy.railway.services;

import lombok.Getter;
import org.fantasy.railway.model.Concession;
import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.util.Now;
import org.fantasy.railway.util.RailwayUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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
    public Passenger addPassenger(String name, LocalDate dateOfBirth) {
        Passenger passenger = Passenger.builder()
                .id(nextId())
                .name(name)
                .dateOfBirth(dateOfBirth)
                .build();
        passengers.add(passenger);
        return passenger;
    }

    @Override
    public Passenger addPassenger(Queue<String> inputs) {
        return addPassenger(
                inputs.poll(),
                LocalDate.parse(inputs.poll()));
    }

    @Override
    public void loadPassengers(String filename) {
        RailwayUtils.parseFile(filename)
                .forEach(this::addPassenger);
    }

    @Override
    public void removePassenger(Passenger passenger) {
        passenger.getTickets().stream()
                .filter(ticket -> ticket.getValidOn().isAfter(Now.localDate()))
                .findFirst()
                .ifPresent(ticket -> {
                    throw new IllegalArgumentException(String.format("Passenger holds future ticket %s", ticket));
                });
        passengers.remove(passenger);
    }

    @Override
    public void addConcession(Passenger passenger, Concession concession) {
        passenger.addConcession(concession);
    }

    @Override
    public void removeConcession(Passenger passenger, Concession concession) {
        passenger.removeConcession(concession);
    }

}
