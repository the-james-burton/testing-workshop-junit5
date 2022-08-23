package org.fantasy.railway.util;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

class GraphUtilsTest {

    void shouldFindTheShortestPath() {
        // TODO EXERCISE 9
    }

    /*
     * <pre>
     *       A
     *      / \
     *     2   3
     *    /     \
     *   /       \
     *  C--3--D-1-E---5---B
     *  |      \  |       |
     *  |      4  6       |
     *  |        \|       |
     *  2         F       15
     *  |         |       |
     *  |         7       |
     *  |         |       |
     *  G----4----H---3---I
     *
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

    void shouldAlwaysCalculateRoute() {
        // TODO EXERCISE 11
    }


}
