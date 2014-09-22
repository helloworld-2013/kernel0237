package mylab.puzzles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Indra Gunawan on 9/21/14.
 */
public class TriangleGraphFileReader {

    public static PathNode readTriangleFromFile(String inputFileName) throws Exception {
        PathNode graph = null;

        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        String input = null;
        boolean topLevel = true;

        List<PathNode> previousNodes = null;

        do {
            input = reader.readLine();
            if (input!=null) {
                List<PathNode> currentNodes = new ArrayList<PathNode>();
                StringTokenizer inputTokenizer = new StringTokenizer(input.trim()," ");
                while (inputTokenizer.hasMoreTokens()) {
                    String token = inputTokenizer.nextToken();
                    int intToken = Integer.parseInt(token);

                    currentNodes.add(new PathNode(intToken));
                }

                if (topLevel) {
                    graph = currentNodes.get(0);
                    topLevel = false;
                } else {
                    int idx = 0;
                    for (PathNode node : previousNodes) {
                        node.getAdjacentNodes().add(currentNodes.get(idx));
                        node.getAdjacentNodes().add(currentNodes.get(idx+1));

                        idx++;
                    }
                }

                previousNodes = currentNodes;
            }
        } while (input!=null);

        return graph;
    }

}
