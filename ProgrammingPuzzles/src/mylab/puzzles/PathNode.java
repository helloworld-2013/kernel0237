package mylab.puzzles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kernel0237 on 9/21/14.
 */
public class PathNode {

    private Integer weight = -1;
    private List<PathNode> adjacentNodes = new ArrayList<PathNode>();

    public PathNode() {

    }

    public PathNode(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<PathNode> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(List<PathNode> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

}
