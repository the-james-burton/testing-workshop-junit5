package org.fantasy.railway.ui;

import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Train;

import java.util.Scanner;

public class StockUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View all rolling stock");
        out.println("2. Add new train");
        out.println("3. Decommission train");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                viewStock(scanner);
                return option;
            case "2":
                addNewTrain(scanner);
                return option;
            case "3":
                removeTrain(scanner);
                return option;
            default:
                out.println("Invalid option, please re-enter.");
                return option;
        }
    }

    private void viewStock(Scanner scanner) {
        out.println(system.getStock().getTrains());
    }

    private void addNewTrain(Scanner scanner) {
        scanner.nextLine();
        out.print("\nPlease enter type of train to add to the stock: ");
        out.println(Train.Type.values());
        String type = scanner.next();
        try {
            Train.Type trainType = Train.Type.valueOf(type);
            system.getStock().addStockFromDepot(trainType);
        } catch (RuntimeException e) {
            out.println(String.format("ERROR: %s ", e.getMessage()));
            return;
        }
        out.println(String.format("Train of type %s added.%n%n", type));
    }

    private void removeTrain(Scanner scanner) {
        scanner.nextLine();
        out.print("\nPlease enter train Id to decommission: ");
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
}
