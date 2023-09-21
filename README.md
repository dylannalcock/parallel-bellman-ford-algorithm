# Parallel Bellman-Ford Algorithm

## Overview

This project represents my successful implementation of the Parallel Bellman-Ford algorithm, designed to compute the shortest paths from a single source vertex to all other vertices in a weighted directed graph. The primary aim was to familiarize myself with writing sequential code and utilizing parallelism to enhance the algorithm's performance. To achieve this, I employed the ForkJoin framework.

The Bellman-Ford algorithm is a crucial tool for finding shortest paths in graphs, especially when dealing with negative-weight edges and identifying negative cost cycles. One practical application of this algorithm is currency arbitrage detection, which involves spotting sequences of imbalanced currency exchange rates to potentially profit from trading activities. The algorithm repeats these exchanges until a profit is realized.

## Sequential Bellman-Ford Pseudocode

Before delving into the parallel implementation, let's briefly review the sequential Bellman-Ford algorithm's pseudocode:

```java
// Initialization
for each vertex v
    dist[v] = GraphUtil.INF
    pred[v] = -1
dist[source] = 0

// Main algorithm
for n times do
    for each vertex v
        dist_copy[v] = dist[v]
    for each edge (v,w)
        if (dist_copy[v] + cost(v,w)) < dist[w]
            // Found a shorter path to w
            dist[w] = dist_copy[v] + cost(v,w)
            pred[w] = v
```

In this pseudocode:
- `n` represents the number of vertices in the graph.
- `dist` and `dist_copy` are arrays that keep track of computed distances.
- `cost(v,w)` signifies the weight of edge `(v,w)`.
- `pred` is an array used to maintain predecessors.

## Project Achievements

In this project, I successfully implemented various components, including `Parser`, `OutSequential`, `OutParallelBad`, `OutParallelLock`, `InParallel`, `ArrayCopyTask`, `RelaxOutTaskBad`, `RelaxOutTaskLock`, and `RelaxInTask`. These components played pivotal roles in introducing parallelism and optimizing the computation of the Bellman-Ford algorithm.

## Part 1: Sequential

### Parser Part 1
Before diving into the Bellman-Ford algorithm, I recognized the importance of parsing the input graph, which is represented as an adjacency matrix, into an adjacency list format. This transformation was essential for running the algorithm efficiently. In this initial parser version, I focused on converting the adjacency matrix into an adjacency list of vertices reached through outgoing edges. 

For this task, I chose to use an ArrayList of HashMaps, which allowed me to efficiently represent the adjacency list. Each element in the ArrayList represented a vertex, and the associated HashMap stored outgoing edges and their costs.

**Implementation**: I implemented the `parse()` method in `main/Parser.java`.

### Bellman-Ford Sequential
In this first version of the Bellman-Ford algorithm, I concentrated on the sequential implementation. The algorithm's `solve()` method consisted of three main parts:

1. Parsing and Initialization: Parsing the adjacency matrix to create adjacency lists (using the method from Parser.java) and initializing essential data structures such as `dist` and `pred`. This part remained sequential in all versions.

2. Running the Bellman-Ford Algorithm: This part involved two substeps: array copying and relaxing the edges. In this sequential version, both array copying and edge relaxing were done sequentially.

3. Finding Negative-Cost Cycles: To detect negative-cost cycles, I used the `getCycle()` method in `GraphUtil.java`. This method returned the list of vertices contributing to a cycle with a negative cost sum, which I then returned from the `solve()` method.

**Implementation**: I implemented this version in `solvers/OutSequential.java`.

## Part 2: Parallel with Outgoing Edges

### Naive ForkJoin
With a better understanding of the Bellman-Ford algorithm, I ventured into parallelizing it to improve performance. I leveraged the Java ForkJoin framework to parallelize both the array copying and edge relaxing parts of the algorithm.

In this version, I focused on parallelizing the edge relaxing step, specifically, the iteration through outgoing edges. The goal was to parallelize the outer loop for each vertex while keeping the inner loop for outgoing edges sequential. It's important to note that due to the nature of this parallelization, the results might be incorrect or correct, which was expected.

**Implementation**: I implemented this version in `solvers/OutParallelBad.java` and created two ForkJoin tasks: `ArrayCopyTask.java` and `RelaxOutTaskBad.java` in the `paralleltasks` folder.

### ForkJoin with Locks
Recognizing the incorrect results from the naive parallel version, I took steps to address this issue by using locks. The solution was to manage locks to prevent race conditions during the parallel execution. 

In this version, I continued working with outgoing edges and implemented a locking mechanism. I used Java's ReentrantLocks to manage concurrency and avoid race conditions, ensuring that parallel execution produced correct results.

**Implementation**: I implemented this version in `solvers/OutParallelLock.java` and created a corresponding ForkJoin task: `paralleltasks/RelaxOutTaskLock.java`. I was able to reuse the `ArrayCopyTask` from the previous version because the array copying process remained the same.

## Part 3: Parallel with Incoming Edges & Short Questions

### Parser Part 2
In preparation for the next Bellman-Ford implementation, I recognized the need to convert the input graph to adjacency lists but this time keeping track of incoming edges. This transformation was crucial for running the algorithm with incoming edges efficiently.

**Implementation**: I implemented the `parseInverse()` method in `main/Parser.java`.

### Another ForkJoin
To address the issues from the naive parallel version, I explored an alternative approach by working with incoming edges instead of outgoing ones. By now, I had a method to create adjacency lists for incoming edges, which I used in this version.

In this version, I continued to use the ForkJoin framework but eliminated the use of locks. Instead, I focused on changing how I relaxed the edges, allowing for efficient parallel execution.

**Implementation**: I implemented this version in `solvers/InParallel.java` and created another ForkJoin task: `paralleltasks/RelaxInTask`. I reused the `ArrayCopyTask` from a previous version, as the array copying process remained the same.

## Project Restrictions

To challenge myself and hone my skills, I adhered to specific project restrictions:
- Utilized Java's built-in data structure classes from `java.util.*`.
- Avoided importing `java.util.Arrays`, which encouraged me to explore parallelism using my own methods.
- Emphasized designing a well-architected codebase, which contributed significantly to my project's success.

## Graph Generation

This project relied on graphs generated by `GraphUtil.generate()`, which represented graphs as adjacency matrices, complete with edge costs. The generator allowed me to control crucial factors, such as the number of vertices, probabilities of forward and backward edges, and cost ranges for edges.

## Sample Settings

For testing and validation, I experimented with various graph generation settings. Here are some noteworthy examples:

- **N=5:** `(0.5, 2, 4), (0.5, -4, -2)` - Typically resulted in a cycle of length 2 or 3.
- **N=10:** `(0.5, 2, 4), (0.5, -4, -2)` - Usually yielded a cycle of length 3 to 8.
- **N=20:** `(0.2, 3, 10), (0.5, -4, -2)` - Generally led to a cycle of length 6 to 10.
- **N=50:** `(0.1, 5, 30), (0.2, -6, -3)` - Often produced a cycle of length 8 to 16.
- **N=50:** `(0.1, 70, 100), (0.2, -6, -3)` - Resulted in a cycle about half of the time.
- **N=100:** `(0.1, 10, 100), (0.1, -10, -1)` - Typically generated a cycle of length 10 to 20.

## Conclusion

This project has been an invaluable learning experience, allowing me to explore the intricacies of parallel computing, algorithm optimization, and concurrency management. My implementations progressively evolved from a sequential approach to parallel versions, addressing issues and achieving better performance along the way.

These implementations not only demonstrate my technical skills but also showcase my ability to analyze and optimize algorithms while adhering to industry-standard practices. I look forward to discussing this project in more detail during interviews, where I can elaborate on the challenges faced, the solutions implemented, and the lessons learned in the process.
