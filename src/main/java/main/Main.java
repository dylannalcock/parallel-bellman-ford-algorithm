package main;

import cse332.interfaces.BellmanFordSolver;
import cse332.graph.GraphUtil;
import solvers.OutSequential;

public class Main {

    /**
     * Use this method to test your BellmanFordSolver implementation.
     */
    public static void main(String[] arg) {
        BellmanFordSolver bfs = new OutSequential();
        int n = 10;
        int[][] g = GraphUtil.generate(n, 0.5, 0.5, 8, 16, -8, 4, 332);
        GraphUtil.printAdjMatrix(g);
        System.out.println(bfs.solve(g, 0));
    }

}
