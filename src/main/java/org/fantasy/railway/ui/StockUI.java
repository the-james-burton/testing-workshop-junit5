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
        out.println("3. Decommission train");
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
        out.print("\nPlease enter type of train to add to the stock: ");
        out.println(Arrays.toString(Train.Type.values()));
        String type = scanner.next();
        Train.Type trainType = Train.Type.valueOf(type);

        Train train = system.getStock().addStockFromDepot(trainType);
        out.format("New train added from depot: %s%n%n", train);
    }

    private void removeTrain(Scanner scanner) {
        scanner.nextLine();
        out.print("\nPlease enter train Id to decommission: ");
        out.println(Train.Type.values());
        Train train = system.getStock().getById(scanner.nextInt());

        system.getStock().decommissionTrain(train);
        out.format("Train decommissioned: %s%n%n", train);
    }
}
