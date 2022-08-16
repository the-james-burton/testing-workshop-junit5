package org.fantasy.railway.util;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(doubles = {1.0d, 2.0d, Math.PI, Integer.MAX_VALUE, Double.MAX_VALUE})
    void shouldParseDouble(Double price) {
        assertThat(RailwayUtils.parsePrice(price))
                .isNotNull()
                .isNotZero()
                .hasScaleOf(2);
    }

    @ParameterizedTest
    @CsvSource({"1.0d,1.00", "2.58d,2.58", "3.14159265359d,3.14", "4.123d, 4.12", "4.125, 4.13", "4.126,4.13"})
    void shouldParseDoubleAsExpected(Double price, String expected) {
        assertThat(RailwayUtils.parsePrice(price)).hasToString(expected);
    }

}
