package it.uniupo.graph.impl;

import upo.graph.base.Edge;
import upo.graph.base.Graph;
import upo.graph.base.VisitResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IncidMatrixUndir implements Graph {

    private List<Edge> edges;
    private Integer[][] matrix;

    /**
     * Constructor of an empty IncidentMatrix, all the fields
     * are initialized to represent an empty Graph with no vertexes/edges.
     */
    protected IncidMatrixUndir() {
        this.edges = new ArrayList<>();
        this.matrix = new Integer[0][0];
    }

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
                    if (e.getSource() > integer || e.getTarget() > integer){
                       int source =  e.getSource() > integer ? e.getSource() - 1 : e.getSource();
                       int target = e.getTarget() > integer ? e.getTarget() - 1 : e.getTarget();
                       return Edge.getEdgeByVertexes(source, target);
                    }
                    else return e;
                })
                .toList();
        this.rebuildMatrix(this.size() - 1);
    }

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
     * @return boolean, the edge is in the list?
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

    @Override
    public int size() {
        return this.matrix.length;
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public boolean isCyclic() {
        return false;
    }

    @Override
    public boolean isDAG() {
        return false;
    }

    @Override
    public VisitResult getBFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        /*
        if (!this.containsVertex(integer))
            throw new IllegalArgumentException("The vertex does not belong to the graph.");

        VisitResult visitResult = new VisitResult(this);
        LinkedList<Integer> queue = new LinkedList<>();
        vertexes.forEach(vert -> visitResult.setColor(vert, VisitResult.Color.WHITE));
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
         */
        return null;
    }

    @Override
    public VisitResult getDFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        return null;
    }

    @Override
    public VisitResult getDFSTOTForest(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        return null;
    }

    @Override
    public VisitResult getDFSTOTForest(Integer[] integers) throws UnsupportedOperationException, IllegalArgumentException {
        return null;
    }

    @Override
    public Integer[] topologicalSort() throws UnsupportedOperationException {
        return new Integer[0];
    }

    @Override
    public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
        return Set.of();
    }

    @Override
    public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
        return Set.of();
    }

    /**
     * Adds one row to the matrix, enlarging the current data structure.
     */
    protected void rebuildMatrix(int vertexSize) {
        this.matrix = new Integer[vertexSize][edges.size()];
        for (int i = 0; i < vertexSize; ++i) {
            for (int j = 0; j < edges.size(); ++j) {
                Edge e = edges.get(j);
                if (e.getSource().equals(i) || e.getTarget().equals(i))
                    matrix[i][j] = 1;
                else
                    matrix[i][j] = 0;
            }
        }
    }

    protected boolean belongsToEdge(Integer i, Edge e) {
        return e.getSource().equals(i) || e.getTarget().equals(i);
    }

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
}
