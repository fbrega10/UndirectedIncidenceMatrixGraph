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

    protected IncidMatrixUndir() {
    }

    public IncidMatrixUndir(int vertexes, int edges) {
        this.vertexes = new ArrayList<>(vertexes);
        this.edges = new ArrayList<>(edges);
        this.matrix = new Integer[vertexes][edges];
    }

    @Override
    public int addVertex() {
        return 0;
    }

    public int addVertex(Integer vertex){
        if (vertex == null || this.vertexes.contains(vertex))
            return -1;
        this.vertexes.add(vertex);
        Integer[][] newMatrix =  new Integer[vertexes.size()][edges.size()];
        for (int i = 0; i < vertexes.size(); ++i)
            for (int j = 0; j < edges.size(); ++j)
                newMatrix[i][j] = this.matrix[i][j];
        for (int i = 0; i < edges.size(); ++i)
            newMatrix[vertexes.size() - 1][i] = 0;
        this.matrix = newMatrix;
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
        if (edges.contains(edge) || edges.contains(Edge.getEdgeByVertexes(edge.getTarget(), edge.getSource())))
            throw new IllegalArgumentException("The edge is already present");

        this.edges.add(edge);
        Integer[][] newMatrix =  new Integer[vertexes.size()][edges.size()];
        for (int i = 0; i < vertexes.size(); ++i)
            for (int j = 0; j < edges.size(); ++j)
                newMatrix[i][j] = this.matrix[i][j];
        for (int i = 0; i < vertexes.size(); ++i)
            newMatrix[i][edges.size() - 1] = 0;
        this.matrix = newMatrix;
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
        return 0;
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
}
