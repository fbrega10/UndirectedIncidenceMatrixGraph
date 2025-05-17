package it.uniupo.graph.impl;

import upo.graph.base.Edge;
import upo.graph.base.WeightedGraph;

import java.util.NoSuchElementException;

public class IncidMatrixUndirWeight extends IncidMatrixUndir implements WeightedGraph {

    protected static final String UNSUPPORTED_IMPLEMENTATION = "Do not implement.";

    protected IncidMatrixUndirWeight() {
        super();
    }

    @Override
    public double getEdgeWeight(Edge edge) throws IllegalArgumentException, NoSuchElementException {
        if (!this.containsEdge(edge))
            throw new NoSuchElementException(String.format("The edge (%d, %d) does not belong to the graph", edge.getSource(), edge.getTarget()));
        return super.matrix[edge.getSource()][edges.indexOf(edge)];
    }

    @Override
    public void setEdgeWeight(Edge edge, double v) throws IllegalArgumentException, NoSuchElementException {
        if (!this.containsEdge(edge))
            throw new NoSuchElementException(String.format("The edge (%d, %d) does not belong to the graph", edge.getSource(), edge.getTarget()));
        int edgeIndex = edges.indexOf(edge);
        super.matrix[edge.getSource()][edgeIndex] = super.matrix[edge.getTarget()][edgeIndex] = v;
    }

    @Override
    public WeightedGraph getBellmanFordShortestPaths(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

    @Override
    public WeightedGraph getDijkstraShortestPaths(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

    @Override
    public WeightedGraph getPrimMST(Integer integer) throws UnsupportedOperationException, IllegalArgumentException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

    @Override
    public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

    @Override
    public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(UNSUPPORTED_IMPLEMENTATION);
    }

}
