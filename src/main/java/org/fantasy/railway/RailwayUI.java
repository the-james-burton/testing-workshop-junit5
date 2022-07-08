package org.fantasy.railway;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Seat;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        out.println("\nPlease select an option:");
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
            out.println(String.format("ERROR: %s " + e.getMessage()));
            return;
        }
        out.println("New passenger added.\n\n");
    }

    private String ticketBooking(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. List all tickets");
        out.println("2. Get ticket quote");
        out.println("3. Reserve a seat for a ticket");
        out.println("4. Purchase a ticket");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                listAllTickets(scanner);
                return option;
            case "2":
                getTicketQuote(scanner);
                return option;
            case "3":
                reserveSeat(scanner);
                return option;
            case "4":
                purchaseTicket(scanner);
                return option;
            default:
                out.println("Invalid option, please re-enter.");
                return option;
        }
    }


    private void listAllTickets(Scanner scanner) {
        out.println(system.getBookings().getTickets());
    }

    private void getTicketQuote(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter details:");
        out.print("  Station from: ");
        Station from = system.getNetwork().stationFromString(scanner.nextLine());
        out.print("  Station to: ");
        Station to = system.getNetwork().stationFromString(scanner.nextLine());
        out.print("  When (yyy-mm-ddThh:mm:ss ");
        LocalDateTime when = LocalDateTime.parse(scanner.nextLine());
        out.print("  Passenger ID: ");
        Passenger passenger = system.getAccounts().getPassengerById(scanner.nextInt());

        Ticket ticket = system.getBookings().ticketQuote(from, to, when, passenger);

        out.println("New ticket quoted.\n\n");
        out.print(ticket);
    }

    private void reserveSeat(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter details:");
        out.print("  Ticket ID: ");
        Ticket ticket = system.getBookings().getTicketById(scanner.nextInt());
        out.print("  When (yyy-mm-ddThh:mm:ss ");
        LocalDateTime when = LocalDateTime.parse(scanner.nextLine());
        out.print("  Must have table?: ");
        Boolean table = scanner.next().equalsIgnoreCase("y");
        out.print("  Must be window?: ");
        Boolean window = scanner.next().equalsIgnoreCase("y");
        out.print("  Must be aisle ?: ");
        Boolean aisle = scanner.next().equalsIgnoreCase("y");
        out.print("  Must face forwards?: ");
        Boolean facingForwards = scanner.next().equalsIgnoreCase("y");

        Seat preferences = Seat.builder()
                .table(table)
                .aisle(aisle)
                .window(window)
                .facingForwards(facingForwards)
                .build();

        system.getBookings().addReservation(ticket, when, preferences);
        out.println("New reservation added to ticket.");
        out.println(String.format("Ticket: %s\n\n", ticket));

    }

    private void purchaseTicket(Scanner scanner) {
    }


}
