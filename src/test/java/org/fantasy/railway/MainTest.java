package org.fantasy.railway;

import org.fantasy.railway.ui.RailwayUI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;
import java.util.Scanner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Mock
    RailwaySystem system;

    @Mock
    RailwayUI railwayUI;

    @Mock
    Scanner scanner;

    @Mock
    PrintStream out;

    @Test
    void shouldStartProgram() {
        Main.startProgram(system, railwayUI, scanner, out);

        verify(system, times(1)).initialize();
        verify(railwayUI, times(1)).setSystem(system);
        verify(railwayUI, times(1)).setOut(out);
        verify(railwayUI, times(1)).initialize();
        verify(railwayUI, times(1)).topMenu(scanner);

    }
}
