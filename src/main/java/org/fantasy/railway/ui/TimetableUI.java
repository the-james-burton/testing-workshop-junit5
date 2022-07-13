package org.fantasy.railway.ui;

import org.fantasy.railway.model.Journey;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Train;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TimetableUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. List services");
        out.println("2. Add new service");
        out.println("3. Schedule train to service");
        out.println("4. Remove train from service");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                listServices(scanner);
                return option;
            case "2":
                addNewService(scanner);
                return option;
            case "3":
                scheduleTrainToService(scanner);
                return option;
            case "4":
                removeStation(scanner);
                return option;
            default:
                out.println("Invalid option, please re-enter.");
                return option;
        }
    }

    private void listServices(Scanner scanner) {
        out.println("Services:");
        out.println(system.getTimetable().getServices());
    }

    private void addNewService(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease define new service: ");
        out.print(" Start time (yyy-mm-ddThh:mm:ss): ");
        LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine());
        out.print(" Starting station: ");
        Station start = system.getNetwork().stationFromString(scanner.nextLine());
        out.print(" Finishing station: ");
        Station finish = system.getNetwork().stationFromString(scanner.nextLine());
        try {
            Journey journey = system.getNetwork().calculateRoute(start, finish);
            system.getTimetable().createNewService(startTime, journey);
        } catch (RuntimeException e) {
            out.println(String.format("ERROR: %s ", e.getMessage()));
            return;
        }
        out.println("New service added.\n\n");
    }

    private void scheduleTrainToService(Scanner scanner) {
        scanner.nextLine();
        out.print("\nPlease enter train Id: ");
        out.println(Train.Type.values());
        out.print("\nPlease enter service Id to assign train to: ");
        out.println(Train.Type.values());
        Integer trainId = scanner.nextInt();
        try {
            system.getStock().decommissionTrain(trainId);
        } catch (RuntimeException e) {
            out.println(String.format("ERROR: %s ", e.getMessage()));
            return;
        }
        out.println(String.format("Train %s decommissioned.%n%n", trainId));

    }

    private void removeStation(Scanner scanner) {

    }
}
