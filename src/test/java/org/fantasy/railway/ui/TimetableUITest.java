package org.fantasy.railway.ui;

import org.fantasy.railway.model.Service;
import org.fantasy.railway.model.Stop;
import org.fantasy.railway.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimetableUITest extends BaseUITest {

    @InjectMocks
    TimetableUI timetableUI;

    @Override
    BaseUI getUI() {
        return timetableUI;
    }

    @BeforeEach
    void setup() {
        super.setup();
        timetableUI = new TimetableUI();
        timetableUI.setSystem(system);
        timetableUI.setOut(out);
    }

    @Test
    void shouldListServices() {
        in = new ByteArrayInputStream("1\n".getBytes());
        Scanner scanner = new Scanner(in);

        List<Service> services = new ArrayList<>();
        services.add(Service.builder().id(1).build());
        services.add(Service.builder().id(2).build());
        services.add(Service.builder().id(3).build());

        when(timetable.getServices()).thenReturn(services);

        timetableUI.displayMenu(scanner);

        String output = outStream.toString();
        assertThat(output)
                .isNotEmpty()
                .contains(services.get(0).toString());
    }

    @Test
    void shouldShowDispatchedServices() {
        in = new ByteArrayInputStream("2\n".getBytes());
        Scanner scanner = new Scanner(in);

        Service service = Service.builder().id(3).build();
        service.setRoute(TestUtils.createTestRoute(service));

        List<Stop> stops = new ArrayList<>(service.getRoute());

        when(timetable.getDispatched()).thenReturn(service.getRoute());

        timetableUI.displayMenu(scanner);

        String output = outStream.toString();
        assertThat(stops)
                .isNotEmpty()
                .allSatisfy(stop -> {
            assertThat(output).contains(stop.getService().getName());
            assertThat(output).contains(stop.getStation().getName());
            assertThat(output).contains(stop.getWhen().toString());
        });
    }

    @Test
    void shouldAddNewService() {
        in = new ByteArrayInputStream("3\n60\nFirst\nThird\n".getBytes());
        Scanner scanner = new Scanner(in);

        Service service1 = Service.builder().id(1).build();
        Service service2 = Service.builder().id(2).build();
        List<Service> services = Arrays.asList(service1, service2);

        when(timetable.createNewServices(inputs.capture())).thenReturn(services);

        timetableUI.displayMenu(scanner);

        String output = outStream.toString();
        assertThat(output).contains(services.toString());

        System.out.println(outStream.toString());
        assertThat(inputs.getValue().poll()).isEqualTo("60");
        assertThat(inputs.getValue().poll()).isEqualTo("First");
        assertThat(inputs.getValue().poll()).isEqualTo("Third");

    }

}
