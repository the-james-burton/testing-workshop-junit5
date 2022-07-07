package org.fantasy.railway;

import lombok.Data;
import org.checkerframework.checker.units.qual.A;
import org.fantasy.railway.services.*;

@Data
public class RailwaySystem {

    private AccountService accounts;
    private BookingService bookings;
    private NetworkService network;
    private StockService stock;
    private TimetableService timetable;

    public void initialize() {
        accounts = new AccountService();
        bookings = new BookingService();
        network = new NetworkService();
        stock = new StockService();
        timetable = new TimetableService();

        bookings.setNetwork(network);
        timetable.setNetworkService(network);
        timetable.setStockService(stock);
    }

}
