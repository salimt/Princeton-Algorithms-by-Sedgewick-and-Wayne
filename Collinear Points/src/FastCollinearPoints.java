import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author: salimt
 */

public class FastCollinearPoints {

    private LineSegment[] segs;

    public FastCollinearPoints(Point[] points) {

        findDuplicate(points);
        ArrayList<LineSegment> foundSegs = new ArrayList<>();

        ArrayList<Point> ends = new ArrayList <>();
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length - 3; i++) {

            ArrayList<Double> slopes = new ArrayList<>();
            for (int j = i + 1; j < sortedPoints.length; j++) {
                slopes.add(sortedPoints[i].slopeTo(sortedPoints[j]));
            }

            ArrayList<Double> reps = new ArrayList<>();
            for (Double val : slopes) {
                if (Collections.frequency(slopes, val) >= 3 && !reps.contains(val)) {
                    reps.add(val);
                }
            }

            for (Double rep : reps) {
                Point[] segment = new Point[2];
                segment[0] = sortedPoints[i];
                for (int s = 0; s < slopes.size(); s++) {
                    if (slopes.get(s).equals(rep)) {
                        segment[1] = sortedPoints[i + s + 1];
                    }
                } if (segment[1] != null && !ends.contains(segment[1]))
                    foundSegs.add(new LineSegment(segment[0], segment[1]));
                    ends.add(segment[1]);
            }
        }
        this.segs = foundSegs.toArray(new LineSegment[foundSegs.size()]);
    }

    /**
     * throws Exception if found any of the elements null
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

    // the number of line segments
    public int numberOfSegments() {
        return segs.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segs, segs.length);
    }


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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
