package org.fantasy.railway.util;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public class Now {

    private static Clock clock = Clock.systemUTC();

    private Now() {
    }

    public static void setClock(Clock cloc) {
        clock = cloc;
    }

    public static LocalDate localDate() {
        return LocalDate.now(clock);
    }

    public static LocalTime localTime() {
        return LocalTime.now(clock);
    }

}