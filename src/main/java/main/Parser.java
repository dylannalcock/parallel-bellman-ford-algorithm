package main;

import cse332.graph.GraphUtil;

import java.util.HashMap;
import java.util.ArrayList;

public class Parser {
    static final int X = GraphUtil.INF;

    /**
     * Parse an adjacency matrix into an adjacency list.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list
     */
    public static ArrayList<HashMap<Integer,Integer>> parse(int[][] adjMatrix) {
        // Initialize the adjacency list
        ArrayList<HashMap<Integer,Integer>> adjList = new ArrayList<>();

        // Iterate over each vertex in the graph
        for (int v = 0; v < adjMatrix.length; v++) {
            HashMap<Integer, Integer> neighbors = new HashMap<>();

            // Iterate over each neighbor
            for (int w = 0; w < adjMatrix[0].length; w++) {
                // If there is an edge, add the neighbor and its distance to the adjacency list
                if (adjMatrix[v][w] != Integer.MAX_VALUE) {
                    neighbors.put(w, adjMatrix[v][w]);
                }
            }

            // Add the vertex and its neighbors to the adjacency list
            adjList.add(neighbors);
        }

        return adjList;
    }

    public static void main (String args[]) {
        int[][] g = {{0, 0, 1}, {0, 0, 1}, {1, 1, 0}};
        int[][] g2 = { {0, 1, 0, 0, 1},
        {1, 0, 1, 1, 1},
        {0, 1, 0, 1, 0},
        {0, 1, 1, 0, 1},
        {1, 1, 0, 1, 0} };

        parse(g2);
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list with incoming edges
     */
    public static Object parseInverse(int[][] adjMatrix) {
        // Initialize the adjacency list
        ArrayList<HashMap<Integer,Integer>> adjList = new ArrayList<>();

        // Iterate over each vertex in the graph
        for (int v = 0; v < adjMatrix.length; v++) {
            HashMap<Integer, Integer> neighbors = new HashMap<>();

            // Iterate over each neighbor
            for (int w = 0; w < adjMatrix[0].length; w++) {
                // If there is an edge, add the neighbor and its distance to the adjacency list
                if (adjMatrix[v][w] != Integer.MAX_VALUE) {
                    neighbors.put(v, adjMatrix[v][w]);
                }
            }

            // Add the vertex and its neighbors to the adjacency list
            adjList.add(neighbors);
        }

        return adjList;
    }

}
