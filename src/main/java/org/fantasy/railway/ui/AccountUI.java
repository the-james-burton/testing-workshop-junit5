package org.fantasy.railway.ui;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fantasy.railway.model.Concession;
import org.fantasy.railway.model.Passenger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * UI class to manage passenger accounts
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountUI extends BaseUI {

    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View passengers");
        out.println("2. Add new passenger");
        out.println("3. Add concession to passenger");
        out.println("4. Load passenger list from file");
        out.println("5. Remove passenger");
        out.println("X. Back to top menu");
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
                addConcessionToPassenger(scanner);
                break;
            case "4":
                loadPassengers(scanner);
                break;
            case "5":
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

        // gather user input...
        out.println("\nPlease enter passenger details:");
        out.print("  Passenger Name: ");
        inputs.add(scanner.nextLine());
        out.print("  Passenger DOB (yyyy-mm-dd): ");
        inputs.add(scanner.nextLine());

        // perform the back end call...
        Passenger passenger = system.getAccounts().addPassenger(inputs);

        // confirm success to the user...
        out.format("New passenger %s added.%n%n", passenger);
    }

    private void addConcessionToPassenger(Scanner scanner) {
        scanner.nextLine();

        // gather user input...
        out.println("\nPlease enter passenger Id:");
        Integer passengerId = scanner.nextInt();
        Passenger passenger = system.getAccounts().getById(passengerId);
        out.println(passenger);
        out.format("%nPlease enter concession (%s) to add to passenger: ", Arrays.asList(Concession.values()));
        scanner.nextLine();
        String input = scanner.nextLine();
        Concession concession = Concession.valueOf(input);

        // perform the back end call...
        passenger.addConcession(concession);

        // confirm success to the user...
        out.format("Concession %s added to %s%n", concession, passenger);
    }

    private void loadPassengers(Scanner scanner) {
        scanner.nextLine();

        // gather user input...
        out.println("\nPlease enter filename:");
        String filename = scanner.nextLine();

        // perform the back end call...
        system.getAccounts().loadPassengers(filename);

        // confirm success to the user...
        out.format("Passenger file %s successfully loaded", filename);
    }

    private void removePassenger(Scanner scanner) {
        scanner.nextLine();

        // gather user input...
        out.println("\nPlease enter passenger Id:");
        Integer passengerId = scanner.nextInt();
        Passenger passenger = system.getAccounts().getById(passengerId);

        // perform the back end call...
        system.getAccounts().removePassenger(passenger);

        // confirm success to the user...
        out.format("Passenger %s removed.%n%n", passenger);
    }

}
