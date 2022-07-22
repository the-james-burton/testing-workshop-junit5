package org.fantasy.railway.ui;

import org.fantasy.railway.model.Train;

import java.util.Arrays;
import java.util.Scanner;

public class StockUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View all rolling stock");
        out.println("2. Add new train");
        out.println("3. Withdraw train from all services");
        out.println("4. Decommission train");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                viewStock();
                break;
            case "2":
                addNewTrain(scanner);
                break;
            case "3":
                withdrawTrain(scanner);
                break;
            case "4":
                removeTrain(scanner);
                break;
            default:
                out.println("Invalid option, please re-enter.");
                break;
        }
        return option;
    }

    private void viewStock() {
        out.println("All rolling stock:");
        out.println(system.getStock().getTrains());
    }

    private void addNewTrain(Scanner scanner) {
        scanner.nextLine();
        out.print("\nPlease enter number of carriages the new train should have: ");
        Integer numberOfCarriages = scanner.nextInt();

        Train train = system.getStock().addStockFromDepot(numberOfCarriages);
        out.format("New train added from depot: %s%n%n", train);
    }

    private void withdrawTrain(Scanner scanner) {
        scanner.nextLine();
        out.print("\nPlease enter train Id to withdraw from services: ");
        Train train = system.getStock().getById(scanner.nextInt());

        system.getStock().withdrawTrain(train);
        out.format("Train decommissioned: %s%n%n", train);
    }

    private void removeTrain(Scanner scanner) {
        scanner.nextLine();
        out.print("\nPlease enter train Id to decommission: ");
        Train train = system.getStock().getById(scanner.nextInt());

        system.getStock().decommissionTrain(train);
        out.format("Train decommissioned: %s%n%n", train);
    }
}
