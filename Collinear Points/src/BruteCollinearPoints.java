import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author: salimt
 */

public class BruteCollinearPoints {

    private LineSegment[] segs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        findDuplicate(points);

        ArrayList<LineSegment> foundSegs = new ArrayList<>();
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for (int p = 0; p < pointsCopy.length - 3; p++) {
            Point pVal = pointsCopy[p];
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                Point qVal = pointsCopy[q];
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    Point rVal = pointsCopy[r];
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        Point sVal = pointsCopy[s];
                        if (pVal.slopeTo(qVal) == pVal.slopeTo(rVal) && pVal.slopeTo(qVal) == pVal.slopeTo(sVal)) {
                            foundSegs.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        } segs = foundSegs.toArray(new LineSegment[foundSegs.size()]);
    }

    /**
     * throws Exception if found illegal argument
     *
     * @param points
     */
    private void findDuplicate(Point[] points) {
        if (points==null) {  throw new IllegalArgumentException("The array \"Points\" is null."); }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) { throw new IllegalArgumentException("The array \"Points\" contains null element."); }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate(s) found."); }
            }
        }
    }

    /**
     * @return number of segments
     */
    public int numberOfSegments() { return segs.length; }

    /**
     * @return segments
     */
    public LineSegment[] segments() { return Arrays.copyOf(segs, segs.length); }


    /**
     * for testing
     * @param args
     */
    public static void main(String[] args) {

        args = new String[]{"insert file path here"};
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
