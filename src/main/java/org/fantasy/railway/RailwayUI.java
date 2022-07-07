package org.fantasy.railway;

import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

public class RailwayUI {

    RailwaySystem system;
    PrintStream out;

    public RailwayUI(RailwaySystem system, PrintStream out) {
        this.system = system;
        this.out = out;
    }

    public void topMenu() {
        Scanner scanner = new Scanner(System.in);
        out.println("Welcome to the Fantasy Railway System!\n\n");
        String lastOption = "";
        while (!lastOption.equalsIgnoreCase("x")) {
            lastOption = displayMenu(scanner);
        }
        out.println("\nExiting System...\n");
    }

    private String displayMenu(Scanner scanner) {
        out.println("Please select an option:");
        out.println("1. Passenger accounts");
        out.println("2. Ticket booking");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                passengerAccounts(scanner);
                return option;
            case "2":
                ticketBooking(scanner);
                return option;
            default:
                out.println("Invalid option, please re-enter.");
                return option;
        }
    }

    private String passengerAccounts(Scanner scanner) {
        out.println("Please select an option:");
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
        out.println("\n\nPlease enter passenger details:");
        out.print("  Passenger Name: ");
        String name = scanner.nextLine();
        out.print("  Passenger DOB (yyyy-mm-dd): ");
        LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
        try {
            system.getAccounts().addPassenger(name, dateOfBirth);
        } catch (RuntimeException e) {
            out.println(String.format("ERROR: %s " + e.getMessage()));
            return;
        }
        out.println("New passenger added.\n\n");
    }

    private String ticketBooking(Scanner scanner) {
        out.println("Please select an option:");
        out.println("1. NOT IMPLEMENTED YET");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                return option;
            default:
                out.println("Invalid option, please re-enter.");
                return option;
        }
    }

}
