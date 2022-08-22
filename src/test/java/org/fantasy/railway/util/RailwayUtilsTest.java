package org.fantasy.railway.util;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class RailwayUtilsTest {

    @Test
    void shouldNotParseFileIfNotExists() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                RailwayUtils.parseFile("does-not-exist.csv")
        );

        String expected = "File does-not-exist.csv not found.";
        String actual = exception.getMessage();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void shouldIgnoreWhitespace() {
        Queue<Queue<String>> results = RailwayUtils.parseFile("test-data-with-whitespace.csv");

        assertThat(results).hasSize(1);

        assertThat(results.poll())
                .hasSize(3)
                .containsExactly("one", "two", "three");

    }

    @Test
    void shouldParseFile() {
        Queue<Queue<String>> results = RailwayUtils.parseFile("test-data-only.csv");

        assertThat(results).hasSize(1);

        assertThat(results.poll())
                .hasSize(3)
                .containsExactly("one", "two", "three");
    }

    void shouldParseDouble(Double price) {
        // TODO EXERCISE 8
    }

    void shouldParseDoubleAsExpected(Double price, String expected) {
        // TODO EXERCISE 8
    }

}
