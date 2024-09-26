import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private QuickFindUF uf2;
    private boolean[][] grid;
    private int openSites;
    private int length;
    private int top;
    private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Given n <= 0");
        }
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new QuickFindUF(n * n + 2);
        openSites = 0;
        length = n - 1;
        top = n * n;
        bottom = n * n + 1;


    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (length == 0) {
            if (!isOpen(row, col)) {
                grid[row][col] = true;
                openSites++;
            }
        }
        else if (row < 0 || row > length || col < 0 || col > length) {
            throw new IllegalArgumentException();
        }
        else {
            // Opens site
            if (!isOpen(row, col)) {
                grid[row][col] = true;
                openSites++;
            }
            // Top left corner
            if (row == 0 && col == 0) {
                // below
                if (isOpen(row + 1, col)) {
                    uf.union(col, length);
                    // uf2.union(col, length + col);
                }
                // right
                if (isOpen(row, col + 1)) {
                    uf.union(col, col + 1);
                    // uf2.union(col, col + 1);
                }
            }
            // Top right corner
            if (row == 0 && col == length) {
                // below
                if (isOpen(row + 1, col)) {
                    uf.union(col, length + col);
                    // uf2.union(col - 1, length + col - 1);
                }
                // left
                if (isOpen(row, col - 1)) {
                    uf.union(col, col - 1);
                    // uf2.union(col, col - 1);
                }
            }

            // Bottom right corner
            if (row == length && col == length) {
                // above
                if (isOpen(row - 1, col)) {
                    uf.union(row * length + col, (row - 1) * length + col);
                    // uf2.union(row * length + col, (row - 1) * length + col);
                }
                // left
                if (isOpen(row, col - 1)) {
                    uf.union(row * length + col, row * length + col - 1);
                    // uf2.union(row * length + col, row * length + col - 1);
                }

            }
            // Bottom left corner
            if (row == length && col == 0) {

                // uf2.union((row - 1) * length + col - 1, bottom);
                // above
                if (isOpen(row - 1, col)) {
                    uf.union(row * length + col, (row - 1) * length + col);
                    // uf2.union(row * length + col, (row - 1) * length + col);
                }
                // right
                if (isOpen(row, col + 1)) {
                    uf.union(row * length + col, row * length + col + 1);
                    // uf2.union(row * length + col, row * length + col + 1);
                }

            }


            // If site is in the first row.
            if (row == 0) {
                // Union with top
                uf.union(col, top);
                // uf2.union(col, top);
                // below
                if (isOpen(row + 1, col)) {
                    uf.union(col, length + col);
                    // uf2.union(col - 1, length + col - 1);
                }
                // left
                if (col > 0 && isOpen(row, col - 1)) {
                    uf.union(col, col - 1);
                    // uf2.union(col - 1, col - 2);
                }
                // right
                if (col < length && isOpen(row, col + 1)) {
                    uf.union(col, col + 1);
                    // uf2.union(col - 1, col);
                }

            }
            // If site is bottom row.
            if (row == length) {
                // union with bottom
                uf.union(row * length + col, bottom);
                // uf2.union((row - 1) * length + col - 1, bottom);
                // above
                if (isOpen(row - 1, col)) {
                    uf.union(row * length + col, (row - 1) * length + col);
                    // uf2.union(row * length + col, (row - 1) * length + col);
                }

                // left
                if (col > 0 && isOpen(row, col - 1)) {
                    uf.union(row * length + col, row * length + col - 1);
                    // uf2.union((row - 1) * length + col - 1, (row - 1) * length + col - 2);
                }
                // right
                if (col < length && isOpen(row, col + 1)) {
                    uf.union(row * length + col, row * length + col + 1);
                    // uf2.union((row - 1) * length + col - 1, (row - 1) * length + col);
                }
            }

            // if site in the middle
            if (row > 0 && row < length) {
                // above
                if (isOpen(row - 1, col)) {
                    uf.union(row * length + col, (row - 1) * length + col);
                    // uf2.union((row - 1) * length + col - 1, (row - 2) * length + col - 1);
                }
                // below
                if (isOpen(row + 1, col)) {
                    uf.union(row * length + col, (row + 1) * length + col);
                    // uf2.union(col - 1, length + col - 1);
                }

                // left
                if (col > 0 && isOpen(row, col - 1)) {
                    uf.union(row * length + col, row * length + col - 1);
                    // uf2.union((row - 1) * length + col - 1, (row - 1) * length + col - 2);
                }
                // right
                if (col < length && isOpen(row, col + 1)) {
                    uf.union(row * length + col, row * length + col + 1);
                    // uf2.union((row - 1) * length + col - 1, (row - 1) * length + col);
                }
            }
            // If site is on the left
            if (col == 0 && row > 0 && row < length) {
                // above
                if (isOpen(row - 1, col)) {
                    uf.union(row * length + col, (row - 1) * length + col);
                    // uf2.union((row - 1) * length + col - 1, (row - 2) * length + col - 1);
                }
                // below
                if (isOpen(row + 1, col)) {
                    uf.union(row * length + col, (row + 1) * length + col);
                    // uf2.union(col - 1, length + col - 1);
                }
                // right
                if (isOpen(row, col + 1)) {
                    uf.union(row * length + col, row * length + col + 1);
                    // uf2.union((row - 1) * length + col - 1, (row - 1) * length + col);
                }
            }

            // If site is on the right
            if (col == length && row > 0 && row < length) {
                // above
                if (isOpen(row - 1, col)) {
                    uf.union(row * length + col, (row - 1) * length + col);
                    // uf2.union((row - 1) * length + col - 1, (row - 2) * length + col - 1);
                }
                // below
                if (isOpen(row + 1, col)) {
                    uf.union(row * length + col, (row + 1) * length + col);
                    // uf2.union(col - 1, length + col - 1);
                }
                // left
                if (isOpen(row, col - 1)) {
                    uf.union(row * length + col, row * length + col - 1);
                    // uf2.union(row * length + col, row * length + col - 1);
                }
            }

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > length || col < 0 || col > length) {
            throw new IllegalArgumentException();
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > length || col < 0 || col > length) {
            throw new IllegalArgumentException();
        }
        if (!grid[row][col]) {
            return false;
        }
        return uf.find(row * length + col) == uf.find(top);


    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation s = new Percolation(1);
        System.out.println(s.isOpen(0, 0));
        s.open(0, 0);
        System.out.println(s.isOpen(0, 0));

    }
}
