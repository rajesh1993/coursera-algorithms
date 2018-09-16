/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: WeightedQuickUnionUF
 *
 *  This program creates an n*n grid that is used to simulate
 *  a percolation system.
 *
 *    - Accepts n and sets up the grid with all sites blocked initially.
 *    - Uses the WeightedQuickUnionUF to set up a connected graph.
 *    - Given a (row, column) it can perform the following:
 *        - Check if the site is open or full
 *        - Open the site if not already open.
 *    - The percolates() method returns whether the system percolates
 *      in its current state.
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// import edu.princeton.cs.algs4.In;

public class Percolation {
    private int N;
    private int openSites;
    private WeightedQuickUnionUF connection;
    private int[][] grid;
    private boolean percolated;
    
    public Percolation(int n) {               // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = n;
        this.openSites = 0;
        this.connection = new WeightedQuickUnionUF(n * n + 2);
        this.percolated = false;
        
        // Initialize grid with n*n dimensions. Default value 0 implies closed.
        this.grid = new int[n][n];
    }
    
    private void validateArgsWithException(int row, int col) {
        if (row - 1 >= this.N || row - 1 < 0 || col - 1 >= this.N || col - 1 < 0)
            throw new IllegalArgumentException();
    }
    private boolean validateRowColumn(int row, int col) {
        return (row - 1 < this.N && row - 1 >= 0 && col - 1 < this.N && col - 1 >= 0);
    }
    
    private boolean isOpenWithoutException(int row, int col) {
        return grid[row-1][col-1] == 1;
    }
    
    private int twoDimToLinear(int row, int col) {
        return (row - 1) * this.N + col - 1;
    }
    public void open(int row, int col) {       // open site (row, col) if it is not open already
        validateArgsWithException(row, col);
        if (isOpen(row, col))
            return;
        grid[row-1][col-1] = 1;
        openSites++;
        // Form a connection to adjacent open sites
        int linearCoords = twoDimToLinear(row, col);
        // Upper block
        if (validateRowColumn(row - 1, col) && isOpenWithoutException(row - 1, col))
            this.connection.union(twoDimToLinear(row - 1, col), linearCoords);
        // Right block
        if (validateRowColumn(row, col + 1) && isOpenWithoutException(row, col + 1))
            this.connection.union(twoDimToLinear(row, col + 1), linearCoords);
        // Left block
        if (validateRowColumn(row, col - 1) && isOpenWithoutException(row, col - 1))
            this.connection.union(twoDimToLinear(row, col - 1), linearCoords);
        // Down block
        if (validateRowColumn(row + 1, col) && isOpenWithoutException(row + 1, col))
            this.connection.union(twoDimToLinear(row + 1, col), linearCoords);
        // Topmost Row connects to Top Percolate Block
        if (row == 1)
            this.connection.union(this.N * this.N, linearCoords);
        // Lowermost Row connects to Bottom Percolate Block
        if (row == this.N)
            this.connection.union(this.N * this.N + 1, linearCoords);
    }
        
    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        validateArgsWithException(row, col);
        return grid[row-1][col-1] == 1;
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        validateArgsWithException(row, col);
        int n = this.N;
        boolean connectedToTop = this.connection.connected(n * n, twoDimToLinear(row, col));
        return connectedToTop;
    }
    public int numberOfOpenSites() {           // number of open sites
        return openSites;
    }
    public boolean percolates() {             // does the system percolate?
        this.percolated = this.connection.connected(this.N * this.N, this.N * this.N + 1);
        return percolated;
    }

   public static void main(String[] args) {   // test client (optional)
//       In file = new In("./test_data/heart25.txt");
//       int n = file.readInt();
//       Percolation p = new Percolation(n);
//       while (!file.isEmpty()){
//           int row = file.readInt();
//           int col = file.readInt();
//           p.open(row, col);
//       }   
//       System.out.println(p.percolates());
   }
}
