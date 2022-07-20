package org.fantasy.railway.ui;

import lombok.Data;
import org.fantasy.railway.RailwaySystem;

import java.io.PrintStream;
import java.util.Scanner;

@Data
public abstract class BaseUI {
    RailwaySystem system;
    PrintStream out;

    abstract String displayMenu(Scanner scanner);

}
