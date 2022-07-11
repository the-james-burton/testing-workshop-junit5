package org.fantasy.railway.ui;

import java.util.Scanner;

public class StockUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View all rolling stock");
        out.println("2. Add new train");
        out.println("3. Remove train");
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
    }

    private void addNewTrain(Scanner scanner) {
        
    }

    private void removeTrain(Scanner scanner) {
        
    }
}
