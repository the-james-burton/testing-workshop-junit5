package org.fantasy.railway.ui;

import org.fantasy.railway.model.Passenger;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Ticket;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * UI class to manage tickets
 */
public class BookingUI extends BaseUI {


    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. View tickets");
        out.println("2. Purchase a ticket");
        out.println("X. Back to top menu");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                viewTickets();
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

    private void viewTickets() {
        out.println(" All Tickets: ");
        out.println(system.getBookings().getTickets()
                .toString().replace("Ticket", "\nTicket"));
    }

    private void purchaseTicket(Scanner scanner) {
        scanner.nextLine();

        // gather user input...
        out.println("\nPlease enter details:");
        out.print("  Station from: ");
        Station from = system.getNetwork().getStationOrThrow(scanner.nextLine());
        out.print("  Station to: ");
        Station to = system.getNetwork().getStationOrThrow(scanner.nextLine());
        out.print("  When (yyy-mm-dd): ");
        LocalDate when = LocalDate.parse(scanner.nextLine());
        out.print("  Passenger ID: ");

        // perform the back end call...
        Passenger passenger = system.getAccounts().getById(scanner.nextInt());
        Ticket ticket = system.getBookings().purchaseTicket(from, to, when, passenger);

        // confirm success to the user...
        out.format("New ticket quoted: %s%n%n", ticket);
    }


}
