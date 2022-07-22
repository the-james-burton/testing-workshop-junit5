package org.fantasy.railway.ui;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Seat;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;

import java.time.LocalDateTime;
import java.util.Scanner;

public class BookingUI extends BaseUI {


    String displayMenu(Scanner scanner) {
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
                listAllTickets();
                break;
            case "2":
                getTicketQuote(scanner);
                break;
            case "3":
                reserveSeat(scanner);
                break;
            case "4":
                purchaseTicket(scanner);
                break;
            default:
                out.println("Invalid option, please re-enter.");
                break;
        }
        return option;
    }


    private void listAllTickets() {
        out.println(" ALl Tickets: ");
        out.println(system.getBookings().getTickets());
    }

    private void getTicketQuote(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter details:");
        out.print("  Station from: ");
        Station from = system.getNetwork().getStationOrThrow(scanner.nextLine());
        out.print("  Station to: ");
        Station to = system.getNetwork().getStationOrThrow(scanner.nextLine());
        out.print("  When (yyy-mm-ddThh:mm:ss ");
        LocalDateTime when = LocalDateTime.parse(scanner.nextLine());
        out.print("  Passenger ID: ");
        Passenger passenger = system.getAccounts().getById(scanner.nextInt());

        Ticket ticket = system.getBookings().ticketQuote(from, to, when, passenger);
        out.format("New ticket quoted: %s%n%n", ticket);
    }

    private void reserveSeat(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter details:");
        out.print("  Ticket ID: ");
        Ticket ticket = system.getBookings().getById(scanner.nextInt());
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

        system.getBookings().addReservation(ticket, when);
        out.println("New reservation added to ticket.");
        out.format("Ticket: %s%n%n", ticket);

    }

    private void purchaseTicket(Scanner scanner) {
        scanner.nextLine();
        out.println("\nPlease enter details:");
        out.print("  Ticket ID: ");
        Ticket ticket = system.getBookings().getById(scanner.nextInt());
        out.print("  Passenger ID: ");
        Passenger passenger = system.getAccounts().getById(scanner.nextInt());

        out.print("  Have you taken payment Y/N: ");
        if (scanner.nextBoolean()) {
            system.getBookings().purchaseTicket(ticket, passenger);
            out.format("Ticket %s purchased.", ticket);
        }
        out.println("Purchase cancelled");

    }


}
