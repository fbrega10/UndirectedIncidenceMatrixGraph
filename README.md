# Incidence Matrix Graph Library

This project provides two Java classes for modeling **undirected graphs** using the **incidence matrix representation**:

- `IncidMatrixUndir`: Standard undirected graphs.
- `IncidMatrixUndirWeight`: Extension that supports **weighted undirected graphs**.

Both classes implement the core graph operations required by the `Graph` and `WeightedGraph` interfaces defined in the `upo.graph.base` package.

## Key Features

- Vertex and edge insertion/removal
- Adjacency queries
- Breadth-First Search (BFS) and Depth-First Search (DFS) traversal (iterative and recursive)
- Cycle detection
- Connected components computation
- Matrix visualization for debugging
- Full support for vertex/edge validation and exception handling

## Weighted Graph Extensions

The `IncidMatrixUndirWeight` class extends the basic implementation to:

- Store and update **edge weights**
- Preserve weights during vertex/edge modifications
- Maintain correct structure after changes

---

## Time and Space Complexity

Let **V** be the number of vertices and **E** the number of edges.

### Time Complexity

| Operation                     | Time Complexity      |
|------------------------------|----------------------|
| `addVertex()`                | O(E)                 |
| `removeVertex(v)`            | O(EV)                |
| `addEdge(e)`                 | O(VE)                |
| `removeEdge(e)`              | O(VE)                |
| `containsVertex(v)`          | O(1)                 |
| `containsEdge(e)`            | O(E)                 |
| `getAdjacent(v)`             | O(E)                 |
| `isAdjacent(u, v)`           | O(E)                 |
| `getBFSTree(v)`              | O(V + E)             |
| `getDFSTree(v)`              | O(V + E)             |
| `isCyclic()`                 | O(V + E)             |
| `connectedComponents()`      | O(V * (V + E))       |

> Note: Due to the matrix representation, adjacency operations involve scanning edges, which adds extra overhead compared to adjacency list implementations.

### Space Complexity

- **Incidence Matrix**: O(V × E)
- **Edge List**: O(E)
- **Visit structures (color, parent, distance)**: O(V)

---

## Build and Test

This project uses **Java JDK 17.0.15 (OpenJDK, amd64)** and **Apache Maven 3.8.7**.

To build the project and run all current tests, execute:

```bash
mvn clean install
mvn clean test

