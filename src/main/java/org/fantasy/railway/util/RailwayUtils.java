package org.fantasy.railway.util;

import com.google.common.base.Preconditions;
import org.fantasy.railway.model.Stop;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Predicate;

public class RailwayUtils {

    /**
     * class is singleton - static method use only
     */
    private RailwayUtils() {
    }

    /**
     * @param filename name of the file in the classpath to parse
     * @return a queue of queues of string, representing parsed rows
     */
    public static Queue<Queue<String>> parseFile(String filename) {
        InputStream filestream = RailwayUtils.class.getClassLoader().getResourceAsStream(filename);
        Preconditions.checkArgument(filestream != null,
                "File %s not found.", filename);

        Queue<Queue<String>> records = new LinkedList<>();
        try (Scanner scanner = new Scanner(filestream)) {
            while (scanner.hasNextLine()) {
                Queue<String> row = parseLine(scanner.nextLine());
                if (row.isEmpty()) {
                    continue;
                }
                records.add(row);
            }
        }
        return records;
    }

    static Predicate<String> comment = row -> (row.isEmpty() || row.startsWith("#"));

    /**
     *
     * @param line the CSV line to split
     * @return a queue of strings from the given line split by comma
     */
    public static Queue<String> parseLine(String line) {
        Queue<String> values = new LinkedList<>();
        try (Scanner parser = new Scanner(line)) {
            parser.useDelimiter(",");
            while (parser.hasNext()) {
                String input = parser.next();
                if (comment.test(input)) {
                    break;
                }
                values.add(input);
            }
        }
        return values;
    }

    /**
     * @return the total time take for this journey
     */
    public static Integer totalTime(List<Stop> route) {
        return (int) route.get(0).getWhen().until(route.get(route.size() - 1).getWhen(), ChronoUnit.MINUTES);
    }

    /**
     *
     * @param price the double to parse
     * @return a BigDecimal representing the given price
     */
    public static BigDecimal parsePrice(Double price) {
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
    }

}
