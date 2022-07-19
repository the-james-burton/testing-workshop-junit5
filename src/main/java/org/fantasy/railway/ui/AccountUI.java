package org.fantasy.railway.ui;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.util.RailwayUtils;

import java.time.LocalDate;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountUI extends BaseUI {

    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View passenger accounts");
        out.println("2. Add new passenger");
        out.println("3. Load passenger list from file");
        out.println("4. Remove passenger");
        out.println("X. ");
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
                out.println("Invalid option, please re-enter.");
                break;
        }
        return option;
    }

    private void viewPassengers() {
        out.println("Passenger list:");
        out.println(system.getAccounts().getPassengers());
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

        RailwayUtils.parseFile(filename).stream()
                .forEach(row -> system.getAccounts().addPassenger(row));
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
