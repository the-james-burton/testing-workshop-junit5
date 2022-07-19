package org.fantasy.railway.ui;

import org.fantasy.railway.model.Journey;
import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Station;
import org.fantasy.railway.model.Train;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TimetableUI extends BaseUI {

    @Override
    String displayMenu(Scanner scanner) {
        out.println("\nPlease select an option:");
        out.println("1. List services");
        out.println("2. Add new service");
        out.println("3. Schedule train to service");
        out.println("4. Remove train from service");
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
            case "3":
                scheduleTrainToService(scanner);
                break;
            case "4":
                removeTrainFromService(scanner);
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

    private void scheduleTrainToService(Scanner scanner) {
        scanner.nextLine();
        out.print("\nPlease enter train Id: ");
        Integer trainId = scanner.nextInt();
        out.print("\nPlease enter service Id to assign train to: ");
        Integer serviceId = scanner.nextInt();
        Train train = system.getStock().getById(trainId);
        Service service = system.getTimetable().getById(serviceId);

        system.getTimetable().schedule(train, service);
        out.format("Train %s scheduled to service %s%n%n", train, service);
    }

    private void removeTrainFromService(Scanner scanner) {
        out.print("\nPlease enter train Id: ");
        Integer trainId = scanner.nextInt();
        out.print("\nPlease enter service Id to remove train from: ");
        Integer serviceId = scanner.nextInt();
        Train train = system.getStock().getById(trainId);
        Service service = system.getTimetable().getById(serviceId);

        system.getTimetable().removeTrainFromService(train, service);
        out.format("Train %s removed from service %s%n%n", train, service);
    }
}
