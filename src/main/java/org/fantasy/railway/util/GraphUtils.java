package org.fantasy.railway.util;

import com.google.common.graph.ValueGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Utilities to work with Guava Graph
 */
public class GraphUtils {

    /**
     * utility class to be used as static only
     */
    private GraphUtils() {
    }

    /**
     * Finds the shortest path from {@code source} to {@code target}.
     * Implementation of Dijkstra's algorithm with a {@link PriorityQueue} and a data structure holding
     * the actual node, its total distance and its predecessor ({@link NodeWrapper}).
     *
     * @param graph  the graph
     * @param source the source node
     * @param target the target node
     * @param <N>    the node type
     * @return the shortest path; or empty list if no path was found
     * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
     */
    @SuppressWarnings("UnstableApiUsage")
    public static <N> List<N> findShortestPath(ValueGraph<N, Integer> graph, N source, N target) {
        Map<N, NodeWrapper<N>> nodeWrappers = new HashMap<>();
        PriorityQueue<NodeWrapper<N>> queue = new PriorityQueue<>();
        Set<N> shortestPathFound = new HashSet<>();

        // Add source to queue
        NodeWrapper<N> sourceWrapper = NodeWrapper.<N>builder().node(source).build();

        nodeWrappers.put(source, sourceWrapper);
        queue.add(sourceWrapper);

        while (!queue.isEmpty()) {
            NodeWrapper<N> nodeWrapper = queue.poll();
            N node = nodeWrapper.getNode();
            shortestPathFound.add(node);

            // Have we reached the target? --> Build and return the path
            if (node.equals(target)) {
                return buildPath(nodeWrapper);
            }

            // Iterate over all neighbors
            Set<N> neighbors = graph.adjacentNodes(node);
            for (N neighbor : neighbors) {
                // Ignore neighbor if shortest path already found
                if (shortestPathFound.contains(neighbor)) {
                    continue;
                }

                // Calculate total distance from start to neighbor via current node
                int distance = graph.edgeValue(node, neighbor).orElseThrow(IllegalStateException::new);
                int totalDistance = nodeWrapper.getTotalDistance() + distance;

                // Neighbor not yet discovered?
                NodeWrapper<N> neighborWrapper = nodeWrappers.get(neighbor);
                if (neighborWrapper == null) {
                    neighborWrapper = NodeWrapper.<N>builder()
                            .node(neighbor)
                            .totalDistance(totalDistance)
                            .predecessor(nodeWrapper)
                            .build();
                    nodeWrappers.put(neighbor, neighborWrapper);
                    queue.add(neighborWrapper);
                }

                // Neighbor discovered, but total distance via current node is shorter?
                // --> Update total distance and predecessor
                else if (totalDistance < neighborWrapper.getTotalDistance()) {
                    neighborWrapper.setTotalDistance(totalDistance);
                    neighborWrapper.setPredecessor(nodeWrapper);

                    // The position in the PriorityQueue won't change automatically
                    // we have to remove and reinsert the node
                    queue.remove(neighborWrapper);
                    queue.add(neighborWrapper);
                }
            }
        }

        // All reachable nodes were visited but the target was not found
        return new ArrayList<>();
    }

    private static <N> List<N> buildPath(NodeWrapper<N> nodeWrapper) {
        List<N> path = new ArrayList<>();
        while (nodeWrapper != null) {
            path.add(nodeWrapper.getNode());
            nodeWrapper = nodeWrapper.getPredecessor();
        }
        Collections.reverse(path);
        return path;
    }
}