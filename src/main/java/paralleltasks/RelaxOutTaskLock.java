package paralleltasks;

import java.util.concurrent.RecursiveAction;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RelaxOutTaskLock extends RecursiveAction {
    private final int[] D;
    private final int[] P;
    private final int[] D2;
    private final List<HashMap<Integer, Integer>> adjList;
    private final int v;
    private final ReentrantLock lock;

    public RelaxOutTaskLock(int[] D, int[] P, int[] D2, List<HashMap<Integer, Integer>> adjList, int v, Lock lock) {
        this.D = D;
        this.P = P;
        this.D2 = D2;
        this.adjList = adjList;
        this.v = v;
        this.lock = (ReentrantLock) lock;
    }

    @Override
    public void compute() {
        lock.lock();
        try {
            // Relax the edges outgoing from vertex v
            HashMap<Integer, Integer> neighbors = adjList.get(v);
            for (int w : neighbors.keySet()) {
                int edgeCost = neighbors.get(w);
                if (D[v] < Integer.MAX_VALUE && D[w] > D[v] + edgeCost) {
                    D[w] = D2[v] + edgeCost;
                    P[w] = v;
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
