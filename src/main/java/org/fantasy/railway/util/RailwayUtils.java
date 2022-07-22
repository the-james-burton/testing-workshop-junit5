package org.fantasy.railway.util;

import com.google.common.base.Preconditions;

import java.io.InputStream;
import java.util.LinkedList;
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

    private static Queue<String> parseLine(String line) {
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
}
