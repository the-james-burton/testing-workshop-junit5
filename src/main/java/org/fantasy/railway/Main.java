package org.fantasy.railway;

import org.fantasy.railway.ui.RailwayUI;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Class used to start the program
 */
public class Main {

    /**
     * Main entry point for application.
     * Creates instances of the system and UI based on runtime needs and starts the application
     * To start this application, simply run this main method with no args
     *
     * @param args no args are required
     */
    public static void main(String[] args) {
        RailwaySystem system = new RailwaySystem();
        RailwayUI ui = new RailwayUI();
        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;
        startProgram(system, ui, scanner, out);
    }

    /**
     * Performs high level wiring of the application and starts the UI
     *
     * @param system the RailwaySystem to use
     * @param ui the RailwayUI to use
     * @param scanner the JDK Scanner to use as UI input
     * @param out the JDK PrintStream to use as UI output
     */
    static void startProgram(RailwaySystem system, RailwayUI ui, Scanner scanner, PrintStream out) {
        system.initialize();
        ui.setSystem(system);
        ui.setOut(out);
        ui.initialize();
        ui.topMenu(scanner);
    }

}
