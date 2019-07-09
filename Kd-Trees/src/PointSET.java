import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.TreeSet;

/**
 * @author: salimt
 */

public class PointSET {

    private TreeSet<Point2D> treeSet;
    private SET<Point2D> pointSet;

    // construct an empty set of points
    public PointSET(){ this.treeSet = new TreeSet<>(); }

    /**
     *
     * @return true if the SET is empty otherwise false
     */
    public boolean isEmpty(){ return treeSet.isEmpty(); }

    /**
     *
     * @return the number of points inserted into the SET
     */
    public int size(){ return treeSet.size(); }

    /**
     * inserts the given point into the set
     * @param p
     */
    public void insert(Point2D p){
        checkNull(p);
        if (!contains(p)) { treeSet.add(p); }
    }

    /**
     *
     * @param p
     * @return true if the SET includes the given point otherwise false
     */
    public boolean contains(Point2D p){
        checkNull(p);
        return treeSet.contains(p);
    }

    /**
     * throws Exception if given object is null
     * @param o
     */
    private void checkNull(Object o) {
        if (o == null) { throw new IllegalArgumentException(); }
    }

    /**
     *
     * @param rect
     * @return the iterable list of points that are within the range of the given rect
     */
    public Iterable<Point2D> range(RectHV rect){
        checkNull(rect);
        TreeSet<Point2D> temp = new TreeSet <>();
        for (Point2D p: treeSet.tailSet(new Point2D(rect.xmin(), rect.ymin()))) {
            if (rect.xmax() >= p.x() && rect.xmin() <= p.x() && rect.ymax() >= p.y() && rect.ymin() <= p.y()) {
                temp.add(p);
            }
        } return temp;
    }

    /**
     *
     * @param p
     * @return the nearest point to the given point
     */
    public Point2D nearest(Point2D p) {
        checkNull(p);
        if (size()<1) { return null; }

        double tempVal = treeSet.first().distanceSquaredTo(p);
        Point2D temp = treeSet.first();
        for (Point2D point: treeSet) {
            if (point.distanceSquaredTo(p) < tempVal) {
                tempVal = point.distanceSquaredTo(p);
                temp = point;
            }
        } return temp;
    }

    public void draw() {

    }

    @Override
    public String toString() {
        return "PointSET{" +
                "treeSet=" + treeSet +
                '}';
    }

}