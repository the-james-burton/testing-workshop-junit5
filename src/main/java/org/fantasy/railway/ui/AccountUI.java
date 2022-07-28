package org.fantasy.railway.ui;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fantasy.railway.model.Passenger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountUI extends BaseUI {

    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View passengers");
        out.println("2. Add new passenger");
        out.println("3. Load passenger list from file");
        out.println("4. Remove passenger");
        out.println("X. Quit");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                viewPassengers();
                break;
            case "2":
                addNewPassenger(scanner);
                break;
            case "3":
                loadPassengers(scanner);
                break;
            case "4":
                removePassenger(scanner);
                break;
            default:
                break;
        }
        return option;
    }

    private void viewPassengers() {
        out.println(" All Passengers:");
        out.println(system.getAccounts().getPassengers()
                .toString().replace("Passenger", "\nPassenger"));
    }

    private void addNewPassenger(Scanner scanner) {
        scanner.nextLine();
        Queue<String> inputs = new LinkedList<>();

        out.println("\nPlease enter passenger details:");
        out.print("  Passenger Name: ");
        inputs.add(scanner.nextLine());
        out.print("  Passenger DOB (yyyy-mm-dd): ");
        inputs.add(scanner.nextLine());

        Passenger passenger = system.getAccounts().addPassenger(inputs);
        out.format("New passenger %s added.%n%n", passenger);
    }

    private void loadPassengers(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter filename:");
        String filename = scanner.nextLine();

        system.getAccounts().loadPassengers(filename);
        out.format("Passenger file %s successfully loaded", filename);
    }

    private void removePassenger(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter passenger Id:");
        Integer passengerId = scanner.nextInt();
        Passenger passenger = system.getAccounts().getById(passengerId);

        system.getAccounts().removePassenger(passenger);
        out.format("Passenger %s removed.%n%n", passenger);
    }

}
