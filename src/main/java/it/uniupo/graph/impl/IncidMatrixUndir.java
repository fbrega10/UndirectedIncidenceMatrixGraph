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
     * 0 if the operation is successfull, in this case the matrix is resized and
     * a new row is added, all its edges are 0.
     */
    public int addVertex(Integer vertex){
        if (vertex == null || this.vertexes.contains(vertex))
            return -1;
        this.vertexes.add(vertex);
        this.matrix = resize(this.matrix, vertexes.size(),edges.size());
        for (int i = 0; i < edges.size(); ++i)
            this.matrix[vertexes.size() - 1][i] = 0;
        return 0;
    }

    @Override
    public Set<Integer> getVertices() {
        if (vertexes.isEmpty())
            return Collections.emptySet();
        else
            return new HashSet<>(vertexes);
    }

    @Override
    public Set<Edge> getEdges() {
        return new HashSet<>(edges);
    }

    @Override
    public boolean containsVertex(Integer integer) {
        return integer != null && this.vertexes.contains(integer);
    }

    @Override
    public void removeVertex(Integer integer) throws NoSuchElementException {
        if (!this.containsVertex(integer))
            throw new NoSuchElementException("No such vertex!");
    }

    @Override
    public void addEdge(Edge edge) throws IllegalArgumentException {
        if (edge == null || !this.containsVertex(edge.getSource()) || !this.containsVertex(edge.getTarget()))
            throw new IllegalArgumentException("Cannot have an edge with invalid source/target");
        if (edges.contains(edge))
            throw new IllegalArgumentException("The edge is already present");

        this.edges.add(edge);
        this.matrix = resize(matrix, vertexes.size(), edges.size());
        for (int i = 0; i < vertexes.size(); ++i)
            this.matrix[i][edges.size() - 1] = 0;
    }

    @Override
    public boolean containsEdge(Edge edge) throws IllegalArgumentException {
        return false;
    }

    @Override
    public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {

    }

    @Override
    public Set<Integer> getAdjacent(Integer integer) throws NoSuchElementException {
        return Set.of();
    }

    @Override
    public boolean isAdjacent(Integer integer, Integer integer1) throws IllegalArgumentException {
        return false;
    }

    @Override
    public int size() {
        if (this.matrix.length == 0)
            return 0;
        return this.matrix[0].length == 0 ? matrix.length : this.matrix[0].length;
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
     * Utility method, internally used to gracefully resize the matrix.
     * @param mat the current matrix
     * @param rowSize the row size of the new matrix
     * @param columnSize the column size of the new matrix
     * @return a copy of the argument passed matrix but resized as wanted.
     */
    protected static Integer[][] resize(Integer[][] mat, int rowSize, int columnSize){
        Integer[][] newMatrix = new Integer[rowSize][columnSize];
        for (int i = 0; i < mat.length; ++i)
            System.arraycopy(mat[i], 0, newMatrix[i], 0, mat[0].length);
        return newMatrix;
    }

}
