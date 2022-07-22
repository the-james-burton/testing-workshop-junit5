package org.fantasy.railway;

import lombok.Data;
import org.fantasy.railway.services.*;

@Data
public class RailwaySystem {

    private AccountService accounts;
    private BookingService bookings;
    private NetworkService network;
    private StockServiceImpl stock;
    private TimetableService timetable;

    public void initialize() {
        accounts = new AccountServiceImpl();
        bookings = new BookingServiceImpl();
        network = new NetworkServiceImpl();
        stock = new StockServiceImpl();
        timetable = new TimetableServiceImpl();

        bookings.setNetwork(network);
        timetable.setNetworkService(network);
        timetable.setStockService(stock);

        bootstrap();
    }

    private void bootstrap() {
        accounts.loadPassengers("passengers.csv");
        network.loadNetwork("network.csv");
    }


}
