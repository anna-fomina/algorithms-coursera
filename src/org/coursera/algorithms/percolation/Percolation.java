package org.coursera.algorithms.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final int n;
    private final int vTop;
    private final int vBottom;
    private boolean[] open;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        this.vTop = 0;
        this.vBottom = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        open = new boolean[n * n];
    }

    private int getArrayNumber(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
        return n * row - (n - col);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        int p = getArrayNumber(row, col);
        if (row > 1 && isOpen(row - 1, col)) uf.union(p, getArrayNumber(row - 1, col));
        if (row < n && isOpen(row + 1, col)) uf.union(p, getArrayNumber(row + 1, col));
        if (col > 1 && isOpen(row, col - 1)) uf.union(p, getArrayNumber(row, col - 1));
        if (col < n && isOpen(row, col + 1)) uf.union(p, getArrayNumber(row, col + 1));

        if (row == 1) uf.union(p, vTop);
        if (row == n) uf.union(p, vBottom);

        open[p - 1] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int p = getArrayNumber(row, col);
        return open[p - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && uf.connected(vTop, getArrayNumber(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean b : open) {
            if (b) count++;
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(vTop, vBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        int number = 3;
        Percolation perc = new Percolation(number);
        System.out.println(perc.numberOfOpenSites());
        perc.open(1, 1);
        System.out.println(perc.percolates());
        perc.open(2, 1);
        System.out.println(perc.percolates());
        perc.open(3, 1);
        System.out.println(perc.percolates());
        System.out.println(perc.numberOfOpenSites());
    }
}