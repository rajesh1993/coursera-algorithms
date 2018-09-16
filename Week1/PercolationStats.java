/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats n T
 *  Dependencies: Percolation.java
 *                StdRandom
 *                StdStats
 *
 *  This program runs T independent trials on an n*n Percolation system.
 *
 *    - Accepts n, T as command line arguments.
 *    - Displays mean, stddev and 95% Confidence Intervals for the T trials.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialData;
    private double meanPercThresh;
    private double stddevPercThresh;
    private double confidenceMultiplier = 1.96;
    
    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trialData = new double[trials];
        double openSites;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            openSites = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (p.isOpen(row, col))
                    continue;
                p.open(row, col);
                openSites++;
            }
            this.trialData[i] = openSites / (n * n);
        }
        this.meanPercThresh = StdStats.mean(this.trialData);
        this.stddevPercThresh = StdStats.stddev(this.trialData);
    }
    public double mean() {                          // sample mean of percolation threshold
        return this.meanPercThresh;
    }
    public double stddev() {                        // sample standard deviation of percolation threshold
        return this.stddevPercThresh;
    }
    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return mean() - this.confidenceMultiplier * stddev() / Math.sqrt(this.trialData.length);
    }
    public double confidenceHi() {                 // high endpoint of 95% confidence interval
        return mean() + this.confidenceMultiplier * stddev() / Math.sqrt(this.trialData.length);
    }

    public static void main(String[] args) {        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        if (n <= 0 || T <= 0)
            throw new IllegalArgumentException();
        PercolationStats ps = new PercolationStats(n, T);
        System.out.printf("mean                    = %f\n", ps.mean());
        System.out.printf("stddev                  = %f\n", ps.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n", ps.confidenceLo(), ps.confidenceHi());
    }
}