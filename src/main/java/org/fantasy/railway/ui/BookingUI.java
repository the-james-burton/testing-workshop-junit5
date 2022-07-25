package org.fantasy.railway.ui;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;

import java.time.LocalDateTime;
import java.util.Scanner;

public class BookingUI extends BaseUI {


    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. List all tickets");
        out.println("2. Purchase a ticket");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                listAllTickets();
                break;
            case "2":
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
    private void purchaseTicket(Scanner scanner) {
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

        Ticket ticket = system.getBookings().purchaseTicket(from, to, when, passenger);
        out.format("New ticket quoted: %s%n%n", ticket);
    }


}
