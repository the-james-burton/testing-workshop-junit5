package org.fantasy.railway.ui;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Stop;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class TimetableUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. List services");
        out.println("2. Show dispatched services");
        out.println("3. Add new service");
        out.println("X. Back to top menu");
        out.print("Option: ");
        String option = scanner.next();
        switch (option) {
            case "1":
                listServices();
                break;
            case "2":
                showDispatchedServices();
                break;
            case "3":
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
        out.println(system.getTimetable().getServices()
                .toString().replace("Service", "\nService"));
    }

    private void showDispatchedServices() {
        out.println("Dispatched:");
        Queue<Stop> dispatched = system.getTimetable().getDispatched();
        while (!dispatched.isEmpty()) {
            Stop stop = dispatched.poll();
            out.format("%s has departed from %s at %s%n",
                    stop.getService().getName(),
                    stop.getStation().getName(),
                    stop.getWhen());
        }
    }

    private void addNewService(Scanner scanner) {
        scanner.nextLine();
        Queue<String> inputs = new LinkedList<>();

        out.println("\nPlease define new service: ");
        out.print(" Frequency (minutes): ");
        inputs.add(scanner.nextLine());
        out.print(" Starting station: ");
        inputs.add(scanner.nextLine());
        out.print(" Finishing station: ");
        inputs.add(scanner.nextLine());

        List<Service> services = system.getTimetable().createNewServices(inputs);
        out.format("New services added: %s%n%n", services);
    }

}
