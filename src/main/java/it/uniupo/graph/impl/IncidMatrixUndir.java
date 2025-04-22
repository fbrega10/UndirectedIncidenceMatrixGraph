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

public class IncidMatrixUndir implements Graph {

    private List<Integer> vertexes;
    private List<Edge> edges;
    private Integer[][] matrix;

    /**
     * Constructor of an empty IncidentMatrix, all the fields
     * are initialized to represent an empty Graph with no vertexes/edges.
     */
    protected IncidMatrixUndir() {
        this.vertexes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.matrix = new Integer[0][0];
    }

    @Override
    public int addVertex() {
        return 0;
    }

    /**
     * @param vertex
     * @return an integer: -1 if the vertex is already present in the matrix
     * 0 if the operation is successfull, then it is resized and
     * a new row is added, all the corresponding edges are 0.
     */
    public int addVertex(Integer vertex) {
        if (vertex == null || this.vertexes.contains(vertex))
            return -1;
        this.vertexes.add(vertex);
        this.rebuildMatrix();
        return 0;
    }

    /**
     * @return A set of vertexes.
     */
    @Override
    public Set<Integer> getVertices() {
        if (vertexes.isEmpty())
            return Collections.emptySet();
        else
            return new HashSet<>(vertexes);
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
        return integer != null && this.vertexes.contains(integer);
    }

    @Override
    public void removeVertex(Integer integer) throws NoSuchElementException {
        if (!this.containsVertex(integer))
            throw new NoSuchElementException("No such vertex!");
        vertexes.remove(integer);
        for (int i = 0; i < edges.size(); ++i) {
            Edge e = edges.get(i);
            if (e.getTarget().equals(integer) || e.getSource().equals(integer))
                edges.remove(e);
        }
        this.rebuildMatrix();
    }

    @Override
    public void addEdge(Edge edge) throws IllegalArgumentException {
        if (edge == null || !this.containsVertex(edge.getSource()) || !this.containsVertex(edge.getTarget()))
            throw new IllegalArgumentException("Cannot have an edge with invalid source/target");
        if (edges.contains(edge))
            throw new IllegalArgumentException("The edge is already present");
        this.edges.add(edge);
        this.rebuildMatrix();
    }

    /**
     * @param edge
     * @return boolean, the edge is in the list?
     * @throws IllegalArgumentException
     */
    @Override
    public boolean containsEdge(Edge edge) throws IllegalArgumentException {
        if (edge == null || !this.vertexes.contains(edge.getTarget()) || !this.vertexes.contains(edge.getSource()))
            throw new IllegalArgumentException("Error, the current edge is invalid");
        return edges.contains(edge);
    }

    /**
     * @param edge
     * @throws IllegalArgumentException the edge is null, an exception is thrown.
     * @throws NoSuchElementException   the edge is not included in the current edges collection.
     */
    @Override
    public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        if (edge == null)
            throw new IllegalArgumentException("Edge cannot be a null pointer, must construct first");
        if (!this.containsEdge(edge))
            throw new NoSuchElementException("The edge is not present, choose a currently included edge!");
        this.edges.remove(edge);
        this.rebuildMatrix();
    }

    /**
     * @param integer
     * @return A set of vertexes adjacent the parameter (vertex).
     * @throws NoSuchElementException in case of
     */
    @Override
    public Set<Integer> getAdjacent(Integer integer) throws NoSuchElementException {
        if (integer == null || !vertexes.contains(integer))
            throw new NoSuchElementException("Vertex is not present!");
        HashSet<Integer> hashSet = new HashSet<>();
        edges.forEach(edge -> {
            if (edge.getTarget().equals(integer))
                hashSet.add(edge.getSource());
            else if (edge.getSource().equals(integer))
                hashSet.add(edge.getTarget());
        });
        return hashSet;
    }

    @Override
    public boolean isAdjacent(Integer integer, Integer integer1) throws IllegalArgumentException {
        if (integer == null || integer1 == null || !vertexes.contains(integer) || !vertexes.contains(integer1))
            throw new IllegalArgumentException("Must set two valid vertexes as function parameters.");
        return edges.stream().anyMatch(edge -> (edge.getSource().equals(integer) && edge.getTarget().equals(integer1))
                || (edge.getSource().equals(integer1) && edge.getTarget().equals(integer)));
    }

    @Override
    public int size() {
        if (this.matrix.length == 0)
            return 0;
        return this.matrix.length * this.matrix[0].length;
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
     * Rebuilds the matrix from scratch.
     */
    protected void rebuildMatrix() {
        this.matrix = new Integer[vertexes.size()][edges.size()];
        for (int i = 0; i < vertexes.size(); ++i) {
            for (int j = 0; j < edges.size(); ++j) {
                Integer vertex = vertexes.get(i);
                Edge e = edges.get(j);
                if (e.getSource().equals(vertex) || e.getTarget().equals(vertex))
                    matrix[i][j] = 1;
                else
                    matrix[i][j] = 0;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Incident matrix : \n");
        for (int i = 0; i < vertexes.size(); ++i) {
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
