/**
 * @author: salimt
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE95 = 1.96;
    private final int trials;
    private final double[] siteData;


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.siteData = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int first = StdRandom.uniform(1, n + 1);
                int second = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(first, second)) {
                    p.open(first, second);
                }
            }
            this.siteData[i] = (double) p.numberOfOpenSites() / (n * n);
        }

    }

    /**
     * test client (described below)
     */
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = "
                + stats.confidenceLo() + ", "
                + stats.confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(siteData);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(siteData);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE95 * stddev() / Math.sqrt(trials);
    }

}
