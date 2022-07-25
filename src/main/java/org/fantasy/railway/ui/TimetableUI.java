package org.fantasy.railway.ui;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TimetableUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. List services");
        out.println("2. Add new service");
        out.println("X. ");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                listServices();
                break;
            case "2":
                addNewService(scanner);
                break;
            default:
                out.println("Invalid option, please re-enter.");
                break;
        }
        return option;
    }

    private void listServices() {
        out.println("Services:");
        out.println(system.getTimetable().getServices());
    }

    private void addNewService(Scanner scanner) {
        scanner.nextLine();
        Queue<String> inputs = new LinkedList<>();

        out.println("\nPlease define new service: ");
        out.print(" Start time (yyy-mm-ddThh:mm:ss): ");
        inputs.add(scanner.nextLine());
        out.print(" Starting station: ");
        inputs.add(scanner.nextLine());
        out.print(" Finishing station: ");
        inputs.add(scanner.nextLine());

        system.getTimetable().createNewService(inputs);
        out.println("New service added.\n\n");
    }

}
