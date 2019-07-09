/**
 * @author: salimt
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private final int size;
    private final WeightedQuickUnionUF sites;
    private final WeightedQuickUnionUF sitesTrack;
    private final int bot;
    private boolean[][] grid;
    private int numOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.numOpenSites = 0;
        this.sites = new WeightedQuickUnionUF(n * n + 2);
        this.sitesTrack = new WeightedQuickUnionUF(n * n + 2);
        this.bot = n * n;
        this.grid = new boolean[n][n];
    }

    /**
     * EFFECTS: opens the site in the given row, col
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        isIllegal(row, col);
        if (!isOpen(row, col)) {
            this.grid[row - 1][col - 1] = true;
            this.numOpenSites++;

            if (row == 1) {
                sites.union(TOP, xyTo1D(row, col));
                sitesTrack.union(TOP, xyTo1D(row, col));
            }
            if (row == this.size) {
                sites.union(bot, xyTo1D(row, col));
            }

            if (row > 1 && isOpen(row - 1, col)) {
                sites.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                sitesTrack.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (row < this.size && isOpen(row + 1, col)) {
                sites.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                sitesTrack.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }

            if (col > 1 && isOpen(row, col - 1)) {
                sites.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                sitesTrack.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (col < this.size && isOpen(row, col + 1)) {
                sites.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                sitesTrack.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    /**
     * @param row
     * @param col
     * @return true if the site is open in the given row,col
     */
    public boolean isOpen(int row, int col) {
        isIllegal(row, col);
        return this.grid[row - 1][col - 1];
    }

    /**
     * @param row
     * @param col
     * @return IllegalArgumentException if row or col is not current in the grid
     */
    private void isIllegal(int row, int col) {
        if (row < 1 || row > this.size || col < 1 || col > this.size) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param row
     * @param col
     * @return true if the site is open and connects to TOP otherwise false
     */
    public boolean isFull(int row, int col) {
        isIllegal(row, col);
        return isOpen(row, col) && sitesTrack.connected(TOP, xyTo1D(row, col));
    }

    /**
     * @return number of sites opened
     */
    public int numberOfOpenSites() {
        return this.numOpenSites;
    }

    /**
     * check if it percolates from TOP to bottom
     *
     * @return true if so otherwise false
     */
    public boolean percolates() {
        return sites.connected(TOP, bot);
    }

    /**
     * converts 2D to 1D
     *
     * @param row
     * @param col
     * @return
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * this.size + col;
    }

}
