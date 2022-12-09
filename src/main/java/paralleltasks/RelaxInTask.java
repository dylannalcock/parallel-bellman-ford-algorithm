package paralleltasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.RecursiveAction;

public class RelaxInTask extends RecursiveAction {

    // Array of distances from the source to each vertex
    private final int[] D;
    // Array of predecessor nodes in the shortest path from the source
    private final int[] P;
    // Array of adjacency lists for each vertex

    private final int[] D2;
    private final ArrayList<HashMap<Integer, Integer>> adjList;
    // The vertex to relax the outgoing edges for

    private final int[][] adjMatrix;

    private final int v;

    public RelaxInTask(int[] D, int[] P, int[] D2, ArrayList<HashMap<Integer, Integer>> adjList,
                       int[][] adjMatrix, int v) {
        this.D = D;
        this.P = P;
        this.D2 = D2;
        this.adjList = adjList;
        this.adjMatrix = adjMatrix;
        this.v = v;
    }

    @Override
    protected void compute() {
        // Get the neighbors of the current vertex
        HashMap<Integer, Integer> neighbors = adjList.get(v);
        System.out.println("neighbors: " + neighbors);


        // Relax the edges for each neighbor of the current vertex
        for (int w : neighbors.keySet()) {
            System.out.println("w:" + w);
            int edgeCost = neighbors.get(w);

            System.out.println("D[v]: " + D[v]);
            System.out.println("D[w]: " + D[w]);
            System.out.println("D2 before: " + Arrays.toString(D2));

            if (D[w] < Integer.MAX_VALUE && D[v] > D2[w] + edgeCost) {
                D[v] = D2[w] + edgeCost;
                System.out.println("D after: " + Arrays.toString(D));
                P[v] = w;
                System.out.println("P: " + Arrays.toString(P));
            }
        }
    }
}
