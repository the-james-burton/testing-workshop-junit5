package org.fantasy.railway.ui;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Scanner;

@Data
@EqualsAndHashCode(callSuper = false)
public class RailwayUI extends BaseUI {

    AccountUI accountUI;
    BookingUI bookingUI;
    NetworkUI networkUI;
    TimetableUI timetableUI;

    public void initialize() {
        accountUI = new AccountUI();
        accountUI.setOut(this.out);
        accountUI.setSystem(this.system);

        bookingUI = new BookingUI();
        bookingUI.setOut(this.out);
        bookingUI.setSystem(this.system);

        networkUI = new NetworkUI();
        networkUI.setOut(this.out);
        networkUI.setSystem(this.system);

        timetableUI = new TimetableUI();
        timetableUI.setOut(this.out);
        timetableUI.setSystem(this.system);
    }

    public void topMenu(Scanner scanner) {
        out.println("Welcome to the Fantasy Railway System!\n\n");
        String lastOption = "";
        while (!lastOption.equalsIgnoreCase("x")) {
            try {
                lastOption = displayMenu(scanner);
            } catch (RuntimeException e) {
                out.format("ERROR : %s", e.getMessage());
                e.printStackTrace(out);
            }
        }
        out.println("\nExiting System...\n");
    }

    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. Passenger accounts");
        out.println("2. Ticket booking");
        out.println("3. Network Management");
        out.println("4. Timetabling");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                accountUI.displayMenu(scanner);
                break;
            case "2":
                bookingUI.displayMenu(scanner);
                break;
            case "3":
                networkUI.displayMenu(scanner);
                break;
            case "4":
                timetableUI.displayMenu(scanner);
                break;
            default:
                out.println("Invalid option, please re-enter.");
                break;
        }
        return option;
    }


}
