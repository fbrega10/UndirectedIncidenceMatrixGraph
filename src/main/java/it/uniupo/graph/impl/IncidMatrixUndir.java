package it.uniupo.graph.impl;

import upo.graph.base.Edge;
import upo.graph.base.Graph;
import upo.graph.base.VisitResult;

import java.util.NoSuchElementException;
import java.util.Set;

public class IncidMatrixUndir implements Graph {

    protected IncidMatrixUndir(){}

    @Override
    public int addVertex() {
        return 0;
    }

    @Override
    public Set<Integer> getVertices() {
        return Set.of();
    }

    @Override
    public Set<Edge> getEdges() {
        return Set.of();
    }

    @Override
    public boolean containsVertex(Integer integer) {
        return false;
    }

    @Override
    public void removeVertex(Integer integer) throws NoSuchElementException {

    }

    @Override
    public void addEdge(Edge edge) throws IllegalArgumentException {

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
