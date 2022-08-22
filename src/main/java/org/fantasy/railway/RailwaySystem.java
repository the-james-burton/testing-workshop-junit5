package org.fantasy.railway;

import lombok.Data;
import org.fantasy.railway.services.AccountService;
import org.fantasy.railway.services.AccountServiceImpl;
import org.fantasy.railway.services.BookingService;
import org.fantasy.railway.services.BookingServiceImpl;
import org.fantasy.railway.services.NetworkService;
import org.fantasy.railway.services.NetworkServiceImpl;
import org.fantasy.railway.services.TimetableService;
import org.fantasy.railway.services.TimetableServiceImpl;

/**
 * Wire up all services with real implementations to form a cohesive whole
 */
@Data
public class RailwaySystem {

    private AccountService accounts;
    private BookingService bookings;
    private NetworkService network;
    private TimetableService timetable;

    /**
     * create new service instances and wire them up as needed
     */
    public void initialize() {
        accounts = new AccountServiceImpl();
        bookings = new BookingServiceImpl();
        network = new NetworkServiceImpl();
        timetable = new TimetableServiceImpl();

        bookings.setNetwork(network);
        timetable.setNetwork(network);

        bootstrap();
    }

    /**
     * Load starting data from the files
     */
    void bootstrap() {
        accounts.loadPassengers("passengers.csv");
        network.loadNetwork("network.csv");
        timetable.loadServices("services.csv");
    }

    void shutdown() {
        if (timetable != null) timetable.shutdown();
    }

}
