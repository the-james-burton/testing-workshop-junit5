package org.fantasy.railway.ui;

import org.fantasy.railway.model.Station;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
                viewNetwork(scanner);
                return option;
            case "2":
                addNewStation(scanner);
                return option;
            case "3":
                removeStation(scanner);
                return option;
            default:
                out.println("Invalid option, please re-enter.");
                return option;
        }
    }

    private void viewNetwork(Scanner scanner) {
        out.println(system.getNetwork().networkToString());
    }

    private void addNewStation(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter station details:");
        out.print("  Station Name: ");
        Station station = system.getNetwork().stationFromString(scanner.nextLine());
        out.print("  Add connected stations ");
        Map<Station, Integer> connections = new HashMap<>();
        out.print("   Enter connected station name: ");
        String input = scanner.nextLine();
        while (!"q".equals(input)) {
            out.print("   Enter connected station distance: ");
            Station connection = system.getNetwork().stationFromString(scanner.nextLine());
            Integer distance = scanner.nextInt();
            connections.put(connection, distance);
            out.print("   Enter connected station name: ");
            input = scanner.nextLine();
        }
        try {
            system.getNetwork().addStation(station, connections);
        } catch (RuntimeException e) {
            out.println(String.format("ERROR: %s ", e.getMessage()));
            return;
        }
        out.println("New station added.\n\n");
        out.println(String.format("%s, %s", station, connections));

    }

    private void removeStation(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter station to remove from the network: ");
        Station station = system.getNetwork().stationFromString(scanner.nextLine());
        try {
            system.getNetwork().removeStation(station);
        } catch (RuntimeException e) {
            out.println(String.format("ERROR: %s ", e.getMessage()));
            return;
        }
        out.println(String.format("Station %s removed.%n%n", station.getName()));

    }

}
