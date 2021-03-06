package org.fantasy.railway.util;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Now {

    private Now() {
    }

    private static Clock clock = Clock.systemUTC();

    public static void setClock(Clock cloc) {
        clock = cloc;
    }

    public static LocalDate localDate() {
        return LocalDate.now(clock);
    }

    public static LocalTime localTime() {
        return LocalTime.now(clock);
    }

    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(clock);
    }

}