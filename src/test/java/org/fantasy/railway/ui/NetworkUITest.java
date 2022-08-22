package org.fantasy.railway.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NetworkUITest extends BaseUITest {

    @InjectMocks
    NetworkUI networkUI;

    @Override
    BaseUI getUI() {
        return networkUI;
    }

    @Test
    void shouldViewNetwork() {
        in = new ByteArrayInputStream("1\n".getBytes());
        Scanner scanner = new Scanner(in);

        String expected = "expected string";
        when(network.networkToString()).thenReturn(expected);

        networkUI.displayMenu(scanner);

        String output = outStream.toString();
        assertThat(output).contains(expected);
    }

    @Test
    void shouldAddNewStation() {
        in = new ByteArrayInputStream("2\nFirst\ny\nSecond\n4\ny\nThird\n6\nn\n".getBytes());
        Scanner scanner = new Scanner(in);

        networkUI.displayMenu(scanner);

        Queue<String> inputs = new LinkedList<>();
        inputs.addAll(Arrays.asList("First", "Second", "4", "Third", "6"));

        verify(network, times(1)).addStation(inputs);

    }

    @Test
    void shouldLoadNetwork() {
        in = new ByteArrayInputStream("3\nfilename.csv\n".getBytes());
        Scanner scanner = new Scanner(in);

        networkUI.displayMenu(scanner);

        verify(network, times(1)).loadNetwork("filename.csv");

        String output = outStream.toString();
        assertThat(output).contains("Network file filename.csv successfully loaded");
    }
}
