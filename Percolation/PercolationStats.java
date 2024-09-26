import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] x;
    private int experiments;
    private int openSites;
    private double time;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials was 0.");
        }

        x = new double[trials];
        experiments = trials;
        Stopwatch stopwatch = new Stopwatch();
        // Run the amount of trials
        for (int attempt = 0; attempt < trials; attempt++) {

            Percolation table = new Percolation(n);
            openSites = 0;

            // Keep running until table percolates
            while (!table.percolates()) {
                int tableRow = StdRandom.uniformInt(0, n);
                int tableCol = StdRandom.uniformInt(0, n);
                if (!table.isOpen(tableRow, tableCol)) {
                    table.open(tableRow, tableCol);
                }
            }

            openSites = table.numberOfOpenSites();
            x[attempt] = (double) openSites / (n * n);
        }
        time = stopwatch.elapsedTime();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return (mean() - (1.96 * stddev()) / Math.sqrt(experiments));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return (mean() + (1.96 * stddev()) / Math.sqrt(experiments));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(400, 1000);
        System.out.println("Mean: " + ps.mean());
        System.out.println("Standard Deviation: " + ps.stddev());
        System.out.println("Confidence Low: " + ps.confidenceLow());
        System.out.println("Confidence High: " + ps.confidenceHigh());
        System.out.println("Elapsed Time: " + ps.time);
    }
}
