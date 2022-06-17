package org.fantasy.railway.util;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class GraphUtilsTest {

    private ValueGraph<String, Integer> graph;

    @BeforeEach
    void setUp() {
        graph = createSampleGraph();
    }

    /**
     * Tests the implementation of Dijkstra's algorithm using the sample graph
     */
    @ParameterizedTest
    @MethodSource("generateData")
    void shouldFindTheShortestPath(String source, String target, List<String> expected) {
        List<String> shortestPath = GraphUtils.findShortestPath(graph, source, target);
        Assertions.assertIterableEquals(shortestPath, expected);
    }

    /**
     * @return a stream of arguments: source, target, expected shortest path
     */
    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of("D", "H", Arrays.asList("D", "C", "G", "H")),
                Arguments.of("A", "F", Arrays.asList("A", "E", "D", "F")),
                Arguments.of("E", "H", Arrays.asList("E", "D", "C", "G", "H")),
                Arguments.of("B", "H", Arrays.asList("B", "E", "D", "C", "G", "H")),
                Arguments.of("B", "I", Arrays.asList("B", "I")),
                Arguments.of("E", "H", Arrays.asList("E", "D", "C", "G", "H"))
        );
    }

    /*
     * <pre>
     *       A
     *      / \
     *    2/   \3
     *    /     \
     *   / 3   1 \    5
     *  C-----D---E-------B
     *  |      \  |       |
     *  |      4\ |6      |
     *  |        \|       |
     * 2|         F       |15
     *  |         |       |
     *  |         |7      |
     *  |         |       |
     *  G---------H-------I
     *       4        3
     * </pre>
     *
     * Graph and test data from the author
     * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
     */
    ValueGraph<String, Integer> createSampleGraph() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.undirected().build();
        graph.putEdgeValue("A", "C", 2);
        graph.putEdgeValue("A", "E", 3);
        graph.putEdgeValue("B", "E", 5);
        graph.putEdgeValue("B", "I", 15);
        graph.putEdgeValue("C", "D", 3);
        graph.putEdgeValue("C", "G", 2);
        graph.putEdgeValue("D", "E", 1);
        graph.putEdgeValue("D", "F", 4);
        graph.putEdgeValue("E", "F", 6);
        graph.putEdgeValue("F", "H", 7);
        graph.putEdgeValue("G", "H", 4);
        graph.putEdgeValue("H", "I", 3);
        return graph;
    }

}
