package org.fantasy.railway.services;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Train;

import java.util.List;

public class TimetableService {

    StockService stock;
    List<Service> services;
    List<Station> stations;

    /**
     * loads a timetable from the given file
     * @param filepath the file to load the timetable from
     */
    void loadServices(String filepath) {
        // TODO load timetable from file
    }

    /**
     * @return a list of services that do not have a train
     */
    List<Service> findUnstockedServices() {
        // TODO
        return null;
    }

    /**
     * Assigns the given train to the given service
     *
     * @param train the train to add to the given service
     * @param service the service to add the train to
     */
    void schedule(Train train, Service service) {
        // TODO
    }
}
