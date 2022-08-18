package org.fantasy.railway.ui;

import lombok.Data;
import org.fantasy.railway.RailwaySystem;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * The UI is split into one class per menu item. This is the base class for all individual UI classes.
 * Subclasses must be able to display their menu and use a given Scanner for system input.
 * A RailwaySystem and PrintStream are provided to the subclass to use for service calls and system output respectively.
 * See RailwayUI for the top level UI displayed on startup
 */
@Data
public abstract class BaseUI {

    RailwaySystem system;
    PrintStream out;

    /**
     * Subclasses must be able to display a menu of user options
     * @param scanner the Scanner to use to take input
     * @return a string of the menu option chosen (used to pass back control when user exists sub menu)
     */
    abstract String displayMenu(Scanner scanner);

}
