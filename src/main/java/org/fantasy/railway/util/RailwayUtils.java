package org.fantasy.railway.util;

import com.google.common.base.Preconditions;

import java.io.InputStream;
import java.util.*;

public class RailwayUtils {

    /**
     * class is singleton - static method use only
     */
    private RailwayUtils() {
    }

    /**
     *
     * @param filename name of the file in the classpath to parse
     * @return a queue of queues of string, representing parsed rows
     */
    public static Queue<Queue<String>> parseFile(String filename) {
        InputStream filestream = RailwayUtils.class.getClassLoader().getResourceAsStream(filename);
        Preconditions.checkArgument(filestream != null,
                "File %s not found.", filename);

        Queue<Queue<String>> records = new LinkedList<>();
        try (Scanner scanner = new Scanner(filestream)) {
        while(scanner.hasNextLine()) {
                records.add(parseLine(scanner.nextLine()));
            }
        }
        return records;
    }

    private static Queue<String> parseLine(String line) {
        Queue<String> values = new LinkedList<>();
        try (Scanner parser = new Scanner(line)) {
            parser.useDelimiter(",");
            while (parser.hasNext()) {
                values.add(parser.next());
            }
        }
        return values;
    }
}
