import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author: salimt
 */

public class Board {

    private final int[][] tiles;
    private final int size;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.tiles = copy_Matrix(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        LinkedList<Integer> list = convert();
        int num = 0;
        for (int i=0; i<list.size(); i++) {
            if (list.get(i) != i+1 && list.get(i)!=0) {
                num++;
            }
        } return num;
    }

    private LinkedList<Integer> convert(){
        LinkedList<Integer> temp = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp.addLast(tiles[i][j]);
            }
        } return temp;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int num = 0;

        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) { continue; }
                num += Math.abs(((tiles[i][j]-1)/size) - i) + Math.abs(((tiles[i][j]-1) % size) - (j % size));
            }
        } return num;
    }

    // is this board the goal board?
    public boolean isGoal() {
        LinkedList<Integer> convertedList = this.convert();
        for (int i=1; i<convertedList.size(); i++) {
            if (convertedList.get(i-1) != i) {
                return false;
            }
        }return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y instanceof Board) {
            Board b = (Board) y;
            return Arrays.deepEquals(b.tiles, tiles);
        } return false;
    }

    /**
     *
     * @return potential legal movements for the player
     */
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<Board>();

        int[] space = findSpace();

        if (space[0]+1 < size) { neighbors.add(new Board(swap(space[0], space[1], space[0]+1, space[1]))); }
        if (space[0]-1 >= 0) { neighbors.add(new Board(swap(space[0], space[1], space[0]-1, space[1]))); }
        if (space[1]+1 < size) { neighbors.add(new Board(swap(space[0], space[1], space[0], space[1]+1))); }
        if (space[1]-1 >= 0) { neighbors.add(new Board(swap(space[0], space[1], space[0], space[1]-1))); }

        return neighbors;
    }

    private int[] findSpace() {
        int[] temp = new int[2];
        for (int i=0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    temp[0] = i;
                    temp[1] = j;
                    break;
                }
            }
        } return temp;
    }

    /**
     * @param oldX
     * @param oldY
     * @param newX
     * @param newY
     * @return new 2D matrix with swapped values
     */
    private int[][] swap(int oldX, int oldY, int newX, int newY) {
        int[][] tilesCopy = copy_Matrix(tiles);

        int temp = tilesCopy[newX][newY];
        tilesCopy[newX][newY] = tilesCopy[oldX][oldY];
        tilesCopy[oldX][oldY] = temp;

        return tilesCopy;
    }

    /**
     * @return a copy of 2D Matrix
     */
    private int[][] copy_Matrix(int[][] tiles) {
        return Arrays.stream(tiles).map(r -> r.clone()).toArray(int[][]::new);
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {

        int[] space = findSpace();
        int[] twin = new int[4];

        for (int i=0; i<size; i++) {
            int[] temp = new int[4];
            temp[0] = i;
            temp[2] = i;
            for (int j=0; j<size; j++) {
                if (space[0]==i && space[1]==j) { break; }
                if (temp[1]!=0) {
                    temp[1] = j;
                } else {
                    temp[3] = j;
                }
            } if (temp[3]!=0) { twin = temp; break; }
        }
        return new Board(swap(twin[0], twin[1], twin[2], twin[3]));
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        // create initial board from file
        args = new String[]{"insert file path here"};
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        int[][] blocks2 = new int[n][n];
        int m;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                m = in.readInt();
                blocks[i][j] = m;
                blocks2[i][j] = m;
            }
    }

}
