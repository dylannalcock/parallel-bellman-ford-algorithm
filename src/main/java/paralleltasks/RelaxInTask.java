package paralleltasks;

import java.util.ArrayList;
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
    private final int w;

    public RelaxInTask(int[] D, int[] P, int[] D2, ArrayList<HashMap<Integer, Integer>> adjList, int w) {
        this.D = D;
        this.P = P;
        this.D2 = D2;
        this.adjList = adjList;
        this.w = w;
    }

    @Override
    protected void compute() {
        // Get the neighbors of the current vertex
        HashMap<Integer, Integer> neighbors = adjList.get(w);

        // Relax the edges for each neighbor of the current vertex
        for (int v : neighbors.values()) {
            int edgeCost = neighbors.get(v); // Get the cost of the edge (v,w)
            if (D[v] < Integer.MAX_VALUE && D[w] > D2[v] + edgeCost) {
                D[w] = D2[v] + edgeCost;
                P[w] = v;
            }
        }
    }
}
