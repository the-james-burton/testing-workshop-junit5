package org.fantasy.railway;

import org.fantasy.railway.ui.RailwayUI;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        RailwaySystem system = new RailwaySystem();
        RailwayUI ui = new RailwayUI();
        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;
        startProgram(system, ui, scanner, out);
    }

    static void startProgram(RailwaySystem system, RailwayUI ui, Scanner scanner, PrintStream out) {
        system.initialize();
        ui.setSystem(system);
        ui.setOut(out);
        ui.initialize();
        ui.topMenu(scanner);
    }

}
