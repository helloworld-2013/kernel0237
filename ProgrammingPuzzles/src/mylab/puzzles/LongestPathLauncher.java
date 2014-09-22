package mylab.puzzles;

/**
 * Created by kernel0237 on 9/21/14.
 */
public class LongestPathLauncher {

    public static void main(String args[]) throws Exception {
        LongestPathTracker tracker = new LongestPathTracker();
        tracker.track("LongestPathInput1.txt");

        System.out.println("The longest path found : " + tracker.getSolutionTotal());
        System.out.println(tracker.getSolutionList());
    }

}
