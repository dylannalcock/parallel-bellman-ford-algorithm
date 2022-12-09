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
    private final int v;

    public RelaxInTask(int[] D, int[] P, int[] D2, ArrayList<HashMap<Integer, Integer>> adjList, int v) {
        this.D = D;
        this.P = P;
        this.D2 = D2;
        this.adjList = adjList;
        this.v = v;
    }

    @Override
    protected void compute() {
        // Get the neighbors of the current vertex
        HashMap<Integer, Integer> neighbors = adjList.get(v);


        // Relax the edges for each neighbor of the current vertex
        for (int w : neighbors.keySet()) {
            int edgeCost = neighbors.get(w);

            if (D[w] < Integer.MAX_VALUE && D[v] > D2[w] + edgeCost) {
                D[v] = D2[w] + edgeCost;
                P[v] = w;
            }
        }
    }
}
