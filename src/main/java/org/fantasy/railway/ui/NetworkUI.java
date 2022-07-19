package org.fantasy.railway.ui;

import org.fantasy.railway.model.Station;

import java.util.*;

public class NetworkUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View network");
        out.println("2. Add new station");
        out.println("3. Remove station");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                viewNetwork();
                break;
            case "2":
                addNewStation(scanner);
                break;
            case "3":
                removeStation(scanner);
                break;
            default:
                out.println("Invalid option, please re-enter.");
                break;
        }
        return option;
    }

    private void viewNetwork() {
        out.println(system.getNetwork().networkToString());
    }

    private void addNewStation(Scanner scanner) {
        scanner.nextLine();
        Queue<String> inputs = new LinkedList<>();

        out.println("\nPlease enter station details:");
        out.print("  Station Name: ");
        inputs.add(scanner.nextLine());
        out.print("  Add connections? (y/n)");
        while (!"y".equalsIgnoreCase(scanner.nextLine())) {
            out.print("   Enter connected station name: ");
            inputs.add(scanner.nextLine());
            out.print("   Enter connected station distance: ");
            inputs.add(scanner.nextLine());
            out.print("  Add another connection? (y/n)");
        }
        Station station = system.getNetwork().addStation(inputs);
        out.format("New station added: %s%n%n", station);

    }

    private void removeStation(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter station to remove from the network: ");
        Station station = system.getNetwork().stationFromString(scanner.nextLine());

        system.getNetwork().removeStation(station);
        out.format("Station %s removed.%n%n", station.getName());

    }

}
