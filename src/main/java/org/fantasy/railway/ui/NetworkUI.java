package org.fantasy.railway.ui;

import org.fantasy.railway.model.Station;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * UI class to manage the railway network
 */
public class NetworkUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View network");
        out.println("2. Add new station");
        out.println("3. Load network from file");
        out.println("x. Back to top menu");
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
                loadNetwork(scanner);
                break;
            default:
                out.println("Invalid option, please re-enter.");
                break;
        }
        return option;
    }

    private void viewNetwork() {
        out.println(system.getNetwork().networkToString()
                .replace(", nodes:", "\n, nodes:")
                .replace(", edges:", "\n, edges:")
                .replace(", [Station", "\n, [Station")
        );
    }

    private void addNewStation(Scanner scanner) {
        scanner.nextLine();
        Queue<String> inputs = new LinkedList<>();

        // gather user input...
        out.println("\nPlease enter station details:");
        out.print("  Station Name: ");
        inputs.add(scanner.nextLine());
        out.print("  Add connections? (y/n)");
        while ("y".equalsIgnoreCase(scanner.nextLine())) {
            out.print("   Enter connected station name: ");
            inputs.add(scanner.nextLine());
            out.print("   Enter connected station distance: ");
            inputs.add(scanner.nextLine());
            out.print("  Add another connection? (y/n)");
        }

        // perform the back end call...
        Station station = system.getNetwork().addStation(inputs);

        // confirm success to the user...
        out.format("New station added: %s%n%n", station);

    }

    private void loadNetwork(Scanner scanner) {
        scanner.nextLine();

        // gather user input...
        out.println("\nPlease enter filename:");
        String filename = scanner.nextLine();

        // perform the back end call...
        system.getNetwork().loadNetwork(filename);

        // confirm success to the user...
        out.format("Network file %s successfully loaded", filename);
    }

}
