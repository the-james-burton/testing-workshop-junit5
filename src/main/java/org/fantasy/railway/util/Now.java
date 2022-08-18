package org.fantasy.railway.util;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class to make unit testing of time based events easier
 * <a href="https://medium.com/agorapulse-stories/how-to-solve-now-problem-in-your-java-tests-7c7f4a6d703c">medium article</a>
 */
public class Now {

    private static Clock clock = Clock.systemUTC();

    /**
     * class is singleton - static method use only
     */
    private Now() {
    }

    /**
     * Allow overriding the clock to assist in testing
     * @param cloc the clock to use when providing dates and times
     */
    public static void setClock(Clock cloc) {
        clock = cloc;
    }

    /**
     * @return a LocalDate from the current Clock
     */
    public static LocalDate localDate() {
        return LocalDate.now(clock);
    }

    /**
     * @return a LocalTime from the current Clock
     */
    public static LocalTime localTime() {
        return LocalTime.now(clock);
    }

}