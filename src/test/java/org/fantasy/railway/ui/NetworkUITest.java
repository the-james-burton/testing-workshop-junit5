package org.fantasy.railway.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Queue;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NetworkUITest extends BaseUITest {

    @Captor
    ArgumentCaptor<Queue<String>> inputs;

    @Captor
    ArgumentCaptor<String> inputString;

    @InjectMocks
    NetworkUI networkUI;

    @Override
    BaseUI getUI() {
        return networkUI;
    }

    @BeforeEach
    void setup() {
        super.setup();
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

        verify(network).addStation(inputs.capture());

        assertThat(inputs.getValue().poll()).isEqualTo("First");
        assertThat(inputs.getValue().poll()).isEqualTo("Second");
        assertThat(inputs.getValue().poll()).isEqualTo("4");
        assertThat(inputs.getValue().poll()).isEqualTo("Third");
        assertThat(inputs.getValue().poll()).isEqualTo("6");

    }

    @Test
    void shouldLoadNetwork() {
        in = new ByteArrayInputStream("3\nfilename.csv\n".getBytes());
        Scanner scanner = new Scanner(in);

        networkUI.displayMenu(scanner);

        verify(network).loadNetwork(inputString.capture());

        String output = outStream.toString();
        assertThat(inputString.getValue()).isEqualTo("filename.csv");
        assertThat(output).contains("Network file filename.csv successfully loaded");
    }

}
