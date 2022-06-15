package org.fantasy.railway.util;

import lombok.Builder;
import lombok.Data;

/**
 * Data structure containing a node, it's total distance from the start and its predecessor.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 * Simplified with Lombok
 */
@Data
@Builder
class NodeWrapper<N> implements Comparable<NodeWrapper<N>> {

    private final N node;
    private int totalDistance;
    private NodeWrapper<N> predecessor;

    @Override
    public int compareTo(NodeWrapper<N> other) {
        return Integer.compare(this.totalDistance, other.totalDistance);
    }

}