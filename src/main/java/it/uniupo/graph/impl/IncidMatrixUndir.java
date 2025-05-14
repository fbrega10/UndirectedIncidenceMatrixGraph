package it.uniupo.graph.impl;

import upo.graph.base.Edge;
import upo.graph.base.Graph;
import upo.graph.base.VisitResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IncidMatrixUndir implements Graph {

    protected List<Edge> edges;
    protected Double[][] matrix;

    /**
     * Constructor of an empty IncidentMatrix, all the fields
     * are initialized to represent an empty Graph with no vertexes/edges.
     */
    protected IncidMatrixUndir() {
        this.edges = new ArrayList<>();
        this.matrix = new Double[0][0];
    }

    /**
     * Rebuilds the matrix after adding a new vertex, increments the rows by one.
     *
     * @return an int that represents the vertix row position in the matrix.
     */
    @Override
    public int addVertex() {
        int vertexIdx = this.size();
        this.rebuildMatrix(matrix.length + 1);
        return vertexIdx;
    }

    /**
     * @return A set of vertexes, empty if there's no vertexes
     */
    @Override
    public Set<Integer> getVertices() {
        return this.size() > 0 ? IntStream.range(0, matrix.length)
                .boxed()
                .collect(Collectors.toSet()) : Collections.emptySet();
    }

    /**
     * @return An HashSet of edges.
     */
    @Override
    public Set<Edge> getEdges() {
        return new HashSet<>(edges);
    }

    /**
     * @param integer
     * @return a boolean whether the vertex is included or not
     */
    @Override
    public boolean containsVertex(Integer integer) {
        return integer != null && integer < this.size();
    }

    /**
     * Removes the vertex function parameter if it's present.
     * Proceeds than to resize the matrix and eventually decrement all the vertexes
     * that have a higher value than the removed vertex by one.
     * It then fixes all the current edges decrementing each vertex that has been decremented
     * accordingly before, to maintain the same matrix.
     *
     * @param integer
     * @throws NoSuchElementException if the vertex does not belong to the graph.
     */
    @Override
    public void removeVertex(Integer integer) throws NoSuchElementException {
        if (!this.containsVertex(integer))
            throw new NoSuchElementException("No such vertex!");
        for (int i = 0; i < edges.size(); ++i) {
            if (matrix[integer][i] == 1) {
                Edge edge = edges.get(i);
                this.removeEdge(edge);
            }
        }
        edges = edges.stream()
                .filter(e -> !this.belongsToEdge(integer, e))
                .map(e -> {
                    if (e.getSource() > integer || e.getTarget() > integer) {
                        int source = e.getSource() > integer ? e.getSource() - 1 : e.getSource();
                        int target = e.getTarget() > integer ? e.getTarget() - 1 : e.getTarget();
                        return Edge.getEdgeByVertexes(source, target);
                    } else return e;
                })
                .toList();
        this.rebuildMatrix(this.size() - 1);
    }

    /**
     * Adds a new edge if not present, with the given source/target vertexes.
     *
     * @param edge
     * @throws IllegalArgumentException if the Edge contains vertexes that do not belong to the graph.
     */
    @Override
    public void addEdge(Edge edge) throws IllegalArgumentException {
        if (edge == null || !this.containsVertex(edge.getSource()) || !this.containsVertex(edge.getTarget()))
            throw new IllegalArgumentException("Cannot have an edge with invalid source/target");
        if (edges.contains(edge) || edges.contains(Edge.getEdgeByVertexes(edge.getTarget(), edge.getSource())))
            return;
        this.edges.add(edge);
        this.rebuildMatrix(this.size());
    }

    /**
     * @param edge
     * @return true is the edge belongs to the E Set of Edges, false otherwise.
     * @throws IllegalArgumentException
     */
    @Override
    public boolean containsEdge(Edge edge) throws IllegalArgumentException {
        if (!this.containsVertex(edge.getTarget()) || !this.containsVertex(edge.getSource()))
            throw new IllegalArgumentException("Vertexes must belong to the graph!");
        return this.edges.contains(edge)
                || this.edges.contains(Edge.getEdgeByVertexes(edge.getTarget(), edge.getSource()));
    }

    /**
     * @param edge
     * @throws IllegalArgumentException One of the vertexes doesn't belong to the Graph's vertexes.
     * @throws NoSuchElementException   the edge is not included in the current edges.
     */
    @Override
    public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        if (!this.containsEdge(edge))
            throw new NoSuchElementException("No such edge.");
        this.edges.remove(edge);
        this.rebuildMatrix(this.size());
    }

    /**
     * @param integer
     * @return A set of vertexes adjacent the parameter (vertex).
     * @throws NoSuchElementException if the vertex does not belong to the Graph.
     */
    @Override
    public Set<Integer> getAdjacent(Integer integer) throws NoSuchElementException {
        if (!this.containsVertex(integer))
            throw new NoSuchElementException("The vertex does not belong to the Graph.");
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < edges.size(); ++i) {
            if (matrix[integer][i] == 1) {
                Edge e = edges.get(i);
                if (e.getTarget().equals(integer))
                    set.add(e.getSource());
                else
                    set.add(e.getTarget());
            }
        }
        return set;
    }

    /**
     * @param integer
     * @param integer1
     * @return boolean indicating whether the two vertexes are adjacent
     * @throws IllegalArgumentException
     */
    @Override
    public boolean isAdjacent(Integer integer, Integer integer1) throws IllegalArgumentException {
        if (!this.containsVertex(integer) || !this.containsVertex(integer1))
            throw new IllegalArgumentException("Make sure all the vertexes are in the Graph");
        for (int i = 0; i < edges.size(); ++i) {
            if (matrix[integer][i] == 1) {
                Edge edge = edges.get(i);
                if (edge.getTarget().equals(integer1) || edge.getSource().equals(integer1))
                    return true;
            }
        }
        return false;
    }

    /**
     * @return The number of vertexes of the Graph.
     */
    @Override
    public int size() {
        return this.matrix.length;
    }

    /**
     * @return False, it is not directed by default. This is an undirected incidence matrix implementation.
     */
    @Override
    public boolean isDirected() {
        return false;
    }

    /**
     * @return True if the cyclic test detects a backward edge in the DFS visit.
     * A backward edge is detected when the new edge has as targe a vertex which has previously
     * been categorized as gray vertex (currently being visited), which denotes that there's a cycle
     * if target node is not it's predecessor (remember that we're in a undirected matrix...).
     */
    @Override
    public boolean isCyclic() {
        VisitResult visitResult = new VisitResult(this);
        IntStream.range(0, matrix.length)
                .boxed()
                .forEach(vert -> visitResult.setColor(vert, VisitResult.Color.WHITE));
        for (int i = 0; i < this.size(); ++i) {
            if (visitResult.getColor(i).equals(VisitResult.Color.WHITE) && isCyclicRic(visitResult, i))
                return true;
        }
        return false;
    }

    /**
     * This is the recursive function call of the isCyclic() method.
     *
     * @param visitResult the visitResult reference being passed in the function call.
     * @param vertex      the current vertex.
     * @return true if the cyclic test is positive otherwise it's false.
     */
    private boolean isCyclicRic(VisitResult visitResult, Integer vertex) {
        visitResult.setColor(vertex, VisitResult.Color.GRAY);
        boolean cyclicFlag = this.getAdjacent(vertex)
                .stream()
                .filter(ver -> visitResult.getColor(ver).equals(VisitResult.Color.GRAY))
                .anyMatch(ver -> !ver.equals(visitResult.getPartent(vertex)));
        if (cyclicFlag)
            return true;
        List<Integer> integersToBeVisited = this.getAdjacent(vertex)
                .stream()
                .filter(ver -> visitResult.getColor(ver).equals(VisitResult.Color.WHITE))
                .toList();
        for (Integer i : integersToBeVisited) {
            visitResult.setParent(i, vertex);
            if (isCyclicRic(visitResult, i))
                return true;
        }
        return false;
    }

    /**
     * @return False, an undirected matrix is by default NOT directed, which makes this method false.
     */
    @Override
    public boolean isDAG() {
        return false;
    }

    /**
     * This is the BFS visit of the Graph, it's backed by a Queue data structure
     * to make sure each vertex is visited in the arrival order and only once.
     * It creates a tree as output which tracks each level and each vertex distance
     * from the source, tracking for each one it's predecessor.
     * Each visit has asymptotic complexity of  O(m + n).
     * N is the number of vertexes, while M is the length of the incident vertexes list.
     *
     * @param integer Source vertex of the Breadth First Search
     * @return a VisitResult object which contains the output of the visit.
     * @throws UnsupportedOperationException Does not, it is supported.
     * @throws IllegalArgumentException      if the vertex does not belong to the graph.
     */
    @Override
    public VisitResult getBFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {

        if (!this.containsVertex(integer))
            throw new IllegalArgumentException("The vertex does not belong to the graph.");

        VisitResult visitResult = new VisitResult(this);
        LinkedList<Integer> queue = new LinkedList<>();
        IntStream.range(0, matrix.length)
                .boxed()
                .forEach(vert -> visitResult.setColor(vert, VisitResult.Color.WHITE));
        queue.addLast(integer);
        visitResult.setDistance(integer, 0);
        visitResult.setColor(integer, VisitResult.Color.GRAY);
        while (!queue.isEmpty()) {
            Integer top = queue.getFirst();
            this.getAdjacent(top).stream()
                    .filter(vert -> visitResult.getColor(vert).equals(VisitResult.Color.WHITE))
                    .forEach(vert -> {
                        visitResult.setColor(vert, VisitResult.Color.GRAY);
                        visitResult.setParent(vert, top);
                        visitResult.setDistance(vert, visitResult.getDistance(top) + 1);
                        queue.addLast(vert);
                    });
            visitResult.setColor(top, VisitResult.Color.BLACK);
            queue.removeFirst();
        }
        return visitResult;
    }

    /**
     * This visit is backed by a stack data structure, which contains each vertex that's being visited.
     * It returns a tree in the VisitResult, which tracks each predecessor for each vertex and all of the
     * start/end time of the visit of each vertex.
     * This is the non-recursive version of the algorithm.
     *
     * @param integer
     * @return
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException
     */
    @Override
    public VisitResult getDFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        if (!this.containsVertex(integer))
            throw new IllegalArgumentException("Vertex does not belong to the Graph");
        VisitResult visitResult = new VisitResult(this);
        IntStream.range(0, matrix.length)
                .boxed()
                .forEach(vertex -> {
                    visitResult.setColor(vertex, VisitResult.Color.WHITE);
                    visitResult.setStartTime(vertex, Integer.MAX_VALUE);
                    visitResult.setEndTime(vertex, Integer.MAX_VALUE);
                });

        Stack<Integer> stack = new Stack<>();
        AtomicInteger time = new AtomicInteger();
        visitResult.setColor(integer, VisitResult.Color.GRAY);
        stack.push(integer);
        while (!stack.isEmpty()) {
            Integer currentVertex = stack.lastElement();
            if (visitResult.getStartTime(currentVertex) == Integer.MAX_VALUE)
                visitResult.setStartTime(currentVertex, time.incrementAndGet());
            this.getAdjacent(currentVertex)
                    .stream()
                    .filter(vert -> visitResult.getColor(vert).equals(VisitResult.Color.WHITE))
                    .findFirst()
                    .ifPresentOrElse(vert -> {
                                stack.push(vert);
                                visitResult.setColor(vert, VisitResult.Color.GRAY);
                                visitResult.setParent(vert, currentVertex);
                            }
                            , () -> {
                                visitResult.setColor(currentVertex, VisitResult.Color.BLACK);
                                Integer i = stack.pop();
                                visitResult.setColor(i, VisitResult.Color.BLACK);
                                visitResult.setEndTime(i, time.incrementAndGet());
                            });
        }
        return visitResult;
    }

    /**
     * Bonus: added as slides show the recursive version of the algorithm.
     * The stack being used is the computer stack that's created within each function call
     * that allocates a new record and replicates all the local variables.
     * To set the right start/end time for each vertex we must use an AtomicInteger
     * which is passed by reference in each function call.
     * It could be an Integer if it wasn't for the lambda function, which requires an Atomic/Volatile
     * data.
     *
     * @param integer
     * @return A VisitResult having visited the tree recursively
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException      as the vertex does not belong to the Graph
     */
    public VisitResult getDFSTreeRic(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        if (!this.containsVertex(integer))
            throw new IllegalArgumentException("Vertex does not belong to the Graph");

        VisitResult visitResult = new VisitResult(this);
        IntStream.range(0, matrix.length)
                .boxed()
                .forEach(vertex -> {
                    visitResult.setColor(vertex, VisitResult.Color.WHITE);
                    visitResult.setStartTime(vertex, Integer.MAX_VALUE);
                    visitResult.setEndTime(vertex, Integer.MAX_VALUE);
                });
        AtomicInteger time = new AtomicInteger(0);
        visitDFSRic(visitResult, integer, time);
        return visitResult;
    }

    /**
     * @param visitResult VisitResult object containing each part of the visit.
     * @param vertex
     * @param time        Recursive part of the getDFSTreeRic class method.
     */
    public void visitDFSRic(VisitResult visitResult, Integer vertex, AtomicInteger time) {
        visitResult.setColor(vertex, VisitResult.Color.GRAY);
        visitResult.setStartTime(vertex, time.incrementAndGet());
        this.getAdjacent(vertex)
                .stream()
                .filter(currentVertex -> visitResult.getColor(currentVertex).equals(VisitResult.Color.WHITE))
                .forEach(currentVertex -> {
                    visitResult.setParent(currentVertex, vertex);
                    visitDFSRic(visitResult, currentVertex, time);
                });
        visitResult.setColor(vertex, VisitResult.Color.BLACK);
        visitResult.setEndTime(vertex, time.incrementAndGet());
    }


    @Override
    public VisitResult getDFSTOTForest(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        if (!this.containsVertex(integer))
            throw new IllegalArgumentException(String.format("The graph does not contain the vertex %d", integer));
        VisitResult visitResult = new VisitResult(this);
        for (int i = 0; i < this.size(); ++i) {
            if (visitResult.getColor(i).equals(VisitResult.Color.WHITE)) {
                VisitResult currentVisit = this.getDFSTree(i);
                IntStream.range(0, this.size())
                        .boxed()
                        .forEach(ver -> {
                            if (currentVisit.getColor(ver).equals(VisitResult.Color.BLACK)){
                                visitResult.setColor(ver, VisitResult.Color.BLACK);
                                visitResult.setEndTime(ver, currentVisit.getEndTime(ver));
                                visitResult.setStartTime(ver, currentVisit.getStartTime(ver));
                                if (currentVisit.getPartent(ver) != null)
                                    visitResult.setParent(ver, currentVisit.getPartent(ver));
                            }
                        });
            }
        }
        return visitResult;
    }

    @Override
    public VisitResult getDFSTOTForest(Integer[] integers) throws UnsupportedOperationException, IllegalArgumentException {
        return null;
    }

    /**
     * Not supported as of the current implementation.
     *
     * @return nothing
     * @throws UnsupportedOperationException
     */
    @Override
    public Integer[] topologicalSort() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot find a topologicalSort of a graph represented as an undirected incident matrix.");
    }

    @Override
    public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Unsupported operation, this is an undirected matrix graph implementation.");
    }

    /**
     * @return All the connected components of the graph represented as Set of Sets
     * @throws UnsupportedOperationException
     */
    @Override
    public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
        Set<Set<Integer>> completeSet = new HashSet<>();
        for (int i = 0; i < this.size(); ++i) {
            VisitResult visit = this.getDFSTreeRic(i);
            Set<Integer> partialSet = this.getVertices()
                    .stream()
                    .filter(vert -> visit.getColor(vert).equals(VisitResult.Color.BLACK))
                    .collect(Collectors.toSet());
            completeSet.add(partialSet);
        }
        return completeSet;
    }

    /**
     * Rebuilds the matrix accordingly to the vertex size parameter (no° of rows).
     */
    protected void rebuildMatrix(int vertexSize) {

        Double[][] matr = new Double[vertexSize][edges.size()];

        if (this.matrix != null && this.matrix.length > 0 && this.matrix[0] != null) {
            int x = this.matrix.length;
            int y = this.matrix[0].length;

            for (int i = 0; i < x && i < vertexSize; ++i) {
                for (int j = 0; j < y && j < edges.size(); ++j) {
                    matr[i][j] = this.matrix[i][j];
                }
            }
        }

        for (int i = 0; i < vertexSize; ++i) {
            for (int j = 0; j < edges.size(); ++j) {
                if (matr[i][j] == null){
                    Edge e = edges.get(j);
                    if (e.getSource().equals(i) || e.getTarget().equals(i))
                    {
                        matr[i][j] = 1.0;
                    } else
                        matr[i][j] = 0.0;
                }
            }
        }
        this.matrix = matr;
    }

    /**
     * Utility method which is useful in the context of undirected graphs.
     * There is no direction, so opposite edges are the same for the matrix (opposite = source
     * and target are switched).
     *
     * @param i Vertex
     * @param e Edge
     * @return true if the edge contains the vertex as source or target.
     */
    protected boolean belongsToEdge(Integer i, Edge e) {
        return e.getSource().equals(i) || e.getTarget().equals(i);
    }

    /**
     * @return String representing a visualization of the matrix, just for debugging purpose,
     * no use at all of this method.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incident matrix : \n");
        for (int i = 0; i < this.size(); ++i) {
            sb.append("[");
            for (int j = 0; j < edges.size(); ++j) {
                sb.append(this.matrix[i][j]);
                if (j + 1 < edges.size())
                    sb.append(", ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IncidMatrixUndir other)) return false;
        return this.getVertices().equals(other.getVertices()) &&
                this.getEdges().equals(other.getEdges());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getEdges(), this.getVertices());
    }
}
