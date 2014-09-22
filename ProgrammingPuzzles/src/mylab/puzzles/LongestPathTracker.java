package mylab.puzzles;

import java.util.*;

/**
 * Created by kernel0237 on 9/21/14.
 */
public class LongestPathTracker {

    private PathNode graph = null;
    private Stack<Integer> tempSolutionList = new Stack<Integer>(), solutionList = new Stack<Integer>();
    private Integer tempSolutionTotal = 0, solutionTotal = 0;

    private void findTheLongestTrack(PathNode parent) {
        for (PathNode child : parent.getAdjacentNodes()) {
            tempSolutionList.push(child.getWeight());
            tempSolutionTotal += child.getWeight();

            if (child.getAdjacentNodes().size() != 0)
                findTheLongestTrack(child);
            else {
                if (tempSolutionTotal>solutionTotal) {
                    solutionTotal = tempSolutionTotal;
                    solutionList = (Stack)tempSolutionList.clone();
                }
            }

            tempSolutionTotal -= child.getWeight();
            tempSolutionList.pop();
        }
    }

    public Integer getSolutionTotal() { return solutionTotal; }

    public List getSolutionList() { return solutionList; }

    public void track(String inputFileName) throws Exception {
        graph = TriangleGraphFileReader.readTriangleFromFile(inputFileName);

        tempSolutionList.push(graph.getWeight());
        tempSolutionTotal = graph.getWeight();

        findTheLongestTrack(graph);
    }

}
