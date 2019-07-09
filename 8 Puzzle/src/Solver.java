import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

/**
 * @author: salimt
 */

public class Solver {

    private Node lastNode = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) { throw new IllegalArgumentException(); }

        MinPQ <Node> queue = new MinPQ <>();
        Node head = new Node(initial, 0, null);
        queue.insert(head);

        while (!queue.isEmpty()) {
            Board currBoard = head.board;

            if (currBoard.twin().isGoal()) {   // checks if puzzle is unsolvable
                return;
            } if (currBoard.isGoal()) {     // checks if current board reached goal
                lastNode = head;
                return;
            }
            for (Board n : currBoard.neighbors()) {
                if (head.prev == null || !n.equals(head.prev.board)) {
                    queue.insert(new Node(n, head.moves + 1, head));
                }
            } head = queue.delMin();
        }
    }

    public static void main(String[] args) {

        args = new String[]{"insert file path here"};
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    /**
     * checks if the goal node found
     */
    public boolean isSolvable() { return lastNode != null; }

    /**
     *
     * @return the number of moves it did take to reach goal
     */
    public int moves() { return isSolvable() ? lastNode.moves : -1; }

    /**
     *
     * @return number of steps taken in order to reach goal state
     */
    public Iterable<Board> solution() {
        if (isSolvable()) {
            Node lastNodeCopy = lastNode;
            LinkedList<Board> seq = new LinkedList<>();
            seq.addLast(lastNodeCopy.board);
            while (lastNodeCopy.prev != null) {
                seq.addFirst(lastNodeCopy.prev.board);
                lastNodeCopy = lastNodeCopy.prev;
            } return seq;
        } return null;
    }

    /**
     * helper
     */
    private class Node implements Comparable <Node> {
        private final Board board;
        private final Node prev;
        private final int moves;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(Node o) { return Integer.compare(board.manhattan()+moves, o.board.manhattan()+o.moves); }
    }
}