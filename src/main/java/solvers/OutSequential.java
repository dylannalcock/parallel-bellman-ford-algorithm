package solvers;

import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.List;

public class OutSequential implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {

        ArrayList<HashMap<Integer, Integer>> adjList = Parser.parse(adjMatrix);
        int[] D = new int[adjMatrix.length];
        int[] P = new int[adjMatrix.length];
        int[] D2 = new int[D.length];
        System.out.println(adjList);
        System.out.println(Arrays.deepToString(adjMatrix));

        // Copy values from D to D2
        // Initialize all nodes to be unreachable from the source
        for (int i = 0; i < adjMatrix.length; i++) {
            D[i] = Integer.MAX_VALUE;
            P[i] = -1;
        }
        // Initialize the source node to have distance 0
        D[source] = 0;



        // Iterate n - 1 times
        for (int n = 0; n < adjMatrix.length; n++) {
            System.out.println("n: " + n);


            // Relax the edges
            for (int v = 0; v < adjMatrix.length; v++) {
                for (int i = 0; i < D.length; i++) {
                    D2[i] = D[i];
                }
                System.out.println("D: " + Arrays.toString(D));
                System.out.println("D2: " + Arrays.toString(D2));
                HashMap<Integer, Integer> neighbors = adjList.get(v);
                System.out.println("neighbors: " + neighbors);

                for (int w : neighbors.keySet()) {
                    System.out.println("P before: " + Arrays.toString(P));

                    int edgeCost = neighbors.get(w);
                    //System.out.println(edgeCost);
                    System.out.println("v: " + v);
                    System.out.println("w: " + w);
                    System.out.println("D[w]: " + D[w]);
                    System.out.println("D2[v]: " + D2[v]);
                    System.out.println("cost: " + edgeCost);
                    if (D[v] < Integer.MAX_VALUE && D[w] > D2[v] + edgeCost) {
                        D[w] = D2[v] + edgeCost;
                        System.out.println("D after: " + Arrays.toString(D));
                        P[w] = v;
                        System.out.println("P: " + Arrays.toString(P));
                    }
                }
            }
        }
        System.out.println(Arrays.toString(P));
        System.out.println(GraphUtil.getCycle(P));
        return GraphUtil.getCycle(P);
    }
}