package org.fantasy.railway;

import org.fantasy.railway.ui.RailwayUI;

public class Main {

    RailwaySystem system;
    RailwayUI ui;

    public static void main(String[] args) {
        Main main = new Main();
        main.system = new RailwaySystem();
        main.system.initialize();
        main.ui = new RailwayUI();
        main.ui.setSystem(main.system);
        main.ui.setOut(System.out);
        main.ui.initialize();
        main.ui.topMenu();
    }

}
