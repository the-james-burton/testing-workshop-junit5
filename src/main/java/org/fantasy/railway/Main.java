package org.fantasy.railway;

import java.util.Scanner;

public class Main {

    RailwaySystem system;
    RailwayUI ui;

    public static void main(String[] args) throws Throwable {
        Main main = new Main();
        main.system = new RailwaySystem();
        main.system.initialize();
        main.ui = new RailwayUI(main.system, System.out);
        main.ui.topMenu();
    }

}
