package org.fantasy.railway.ui;

import lombok.Data;

import java.time.LocalDate;
import java.util.Scanner;

@Data
public class AccountUI extends BaseUI {

    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View passenger accounts");
        out.println("2. Add new passenger");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                viewPassengers(scanner);
                return option;
            case "2":
                addNewPassenger(scanner);
                return option;
            default:
                out.println("Invalid option, please re-enter.");
                return option;
        }
    }

    private void viewPassengers(Scanner scanner) {
        out.println("Passenger list:");
        out.println(system.getAccounts().getPassengers());
    }

    private void addNewPassenger(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter passenger details:");
        out.print("  Passenger Name: ");
        String name = scanner.nextLine();
        out.print("  Passenger DOB (yyyy-mm-dd): ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        try {
            system.getAccounts().addPassenger(name, dateOfBirth);
        } catch (RuntimeException e) {
            out.println(String.format("ERROR: %s ", e.getMessage()));
            return;
        }
        out.println("New passenger added.\n\n");
    }

}
