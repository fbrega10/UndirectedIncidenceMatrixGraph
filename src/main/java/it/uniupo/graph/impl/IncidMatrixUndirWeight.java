package it.uniupo.graph.impl;

import upo.graph.base.Edge;
import upo.graph.base.VisitResult;
import upo.graph.base.WeightedGraph;

import java.util.NoSuchElementException;
import java.util.Set;

public class IncidMatrixUndirWeight extends IncidMatrixUndir implements WeightedGraph {

    protected IncidMatrixUndirWeight(){
       super();
    }

    @Override
    public double getEdgeWeight(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        super.containsEdge(edge);
        return super.matrix[edge.getSource()][edge.getSource()];
    }

    @Override
    public void setEdgeWeight(Edge edge, double v) throws IllegalArgumentException, NoSuchElementException {
        super.containsEdge(edge);
        super.matrix[edge.getSource()][edge.getTarget()] = super.matrix[edge.getTarget()][edge.getSource()] = v;
    }

    @Override
    public WeightedGraph getBellmanFordShortestPaths(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException("Do not implement.");
    }

    @Override
    public WeightedGraph getDijkstraShortestPaths(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException("Do not implement.");
    }

    @Override
    public WeightedGraph getPrimMST(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException("Do not implement.");
    }

    @Override
    public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Do not implement.");
    }

    @Override
    public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Do not implement.");
    }

    @Override
    public int addVertex() {
        return super.addVertex();
    }

    @Override
    public Set<Integer> getVertices() {
        return super.getVertices();
    }

    @Override
    public Set<Edge> getEdges() {
        return super.getEdges();
    }

    @Override
    public boolean containsVertex(Integer integer) {
        return super.containsVertex(integer);
    }

    @Override
    public void removeVertex(Integer integer) throws NoSuchElementException {
        super.removeVertex(integer);
    }

    @Override
    public void addEdge(Edge edge) throws IllegalArgumentException {
        super.addEdge(edge);
    }

    @Override
    public boolean containsEdge(Edge edge) throws IllegalArgumentException {
        return super.containsEdge(edge);
    }

    @Override
    public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        super.removeEdge(edge);
    }

    @Override
    public Set<Integer> getAdjacent(Integer integer) throws NoSuchElementException {
        return super.getAdjacent(integer);
    }

    @Override
    public boolean isAdjacent(Integer integer, Integer integer1) throws IllegalArgumentException {
        return super.isAdjacent(integer, integer1);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isDirected() {
        return super.isDirected();
    }

    @Override
    public boolean isCyclic() {
        return super.isCyclic();
    }

    @Override
    public boolean isDAG() {
        return super.isDAG();
    }

    @Override
    public VisitResult getBFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        return super.getBFSTree(integer);
    }

    @Override
    public VisitResult getDFSTree(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        return super.getDFSTree(integer);
    }

    @Override
    public VisitResult getDFSTOTForest(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        return super.getDFSTOTForest(integer);
    }

    @Override
    public VisitResult getDFSTOTForest(Integer[] integers) throws UnsupportedOperationException, IllegalArgumentException {
        return super.getDFSTOTForest(integers);
    }

    @Override
    public Integer[] topologicalSort() throws UnsupportedOperationException {
        return super.topologicalSort();
    }

    @Override
    public Set<Set<Integer>> stronglyConnectedComponents() throws UnsupportedOperationException {
        return super.stronglyConnectedComponents();
    }

    @Override
    public Set<Set<Integer>> connectedComponents() throws UnsupportedOperationException {
        return super.connectedComponents();
    }
}
